package com.example.data.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.data.repository.firebase.FirebaseAuthRepository
import com.example.data.data.repository.firebase.FirebaseRepository
import com.example.data.data.repository.livedataTest.LiveDataRepository
import com.example.data.data.repository.models.FirebaseAddressSaveParams
import com.example.data.data.repository.room.CacheStorageInterface
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.data.data.repository.sharedPrefs.settingsStorage.SettingsStorageInterface
import com.example.data.data.repository.utils.DataConverter
import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.models.saveParams.SaveParamsRoom
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.domain.domain.utils.ALLDEVICES_ROOM_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

private const val TAG = "AddressRep"

class AddressRepositoryImpl(
    private val allDevicesRoomIcon:Int,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val cacheStorageInterface: CacheStorageInterface,
    private val settingsStorageInterface: SettingsStorageInterface
):AddressRepository, LiveDataRepository{

    private fun getKey(addressKey: String?):String{
        return addressKey ?: settingsStorageInterface.getSelectedAddressKey()
    }

    //fun currentAddress():String = settingsStorageInterface.getSelectedAddressKey()

    override suspend fun saveAddress(address: SaveParamsAddress, usersUid: List<String>): String? {

        Log.i(TAG, "Saving address with a:$address, uid:$usersUid")

        val params = FirebaseAddressSaveParams(
            name = address.name,
            wifiSSID = address.wifiSSID
        )

        val key = firebaseRepository.saveAddress(address = params) ?: return null

        val newAddress = Address(
            key = key,
            name = address.name,
            wifiSSID = address.wifiSSID
        )

        repeat(usersUid.size) {

            firebaseRepository.addAvailableAddressKey(addressKey = key, userUid = usersUid[it])

        }

        cacheStorageInterface.addAddress(address = newAddress)

        val currentUserUid = firebaseAuthRepository.getCurrentUser().uid

        cacheStorageInterface.addAvailableAddressKey(addressKey = key, userUid = currentUserUid)

        addRoom(
            addressKey = key,
            room = Room(
                id = ALLDEVICES_ROOM_ID.toLong(),
                name = ALLDEVICES_ROOM_NAME,
                icon = allDevicesRoomIcon,
                devicesIdsInRoom = emptyList()
            )
        )

        settingsStorageInterface.changeSelectedAddress(selectedAddressKey = key)

        return key
    }

    override suspend fun addAddress(address: Address): Boolean {

        Log.i(TAG, "Saving address with a:$address")

        TODO()
    }

    override suspend fun deleteAddress(addressKey: String): Boolean {

        Log.i(TAG, "Deleting address with aKey:$addressKey")

        val uid = firebaseAuthRepository.getCurrentUser().uid

        cacheStorageInterface.removeAvailableAddressKey(addressKey = addressKey, userUid = uid)

        cacheStorageInterface.deleteAddress(addressKey = addressKey)

        firebaseRepository.deleteAddress(addressKey = addressKey)

        return true

    }

    override suspend fun getAddress(addressKey: String): Address {

        Log.i(TAG, "Getting address with aKey:$addressKey")

        val cacheAddress = cacheStorageInterface.getAddress(addressKey)

        if(cacheAddress != null) {

            CoroutineScope(Dispatchers.IO).launch {
                val fbAddress = DataConverter.addressFirebase(firebaseRepository.getAddress(addressKey))

                cacheStorageInterface.addAddress(fbAddress)
            }

            return cacheAddress

        }else {

            val fbAddress = DataConverter.addressFirebase(firebaseRepository.getAddress(addressKey))

            cacheStorageInterface.addAddress(fbAddress)

            return fbAddress

        }
    }

    override suspend fun addRoom(addressKey: String?, room: SaveParamsRoom): Long {

        Log.i(TAG, "Adding room with addressKey: $addressKey, room: $room")

        val key = getKey(addressKey)

        val id = cacheStorageInterface.addRoom(addressKey = key, room = room)

        val fbRoom = DataConverter.roomFirebase(cacheStorageInterface.getRoom(key, id)!!)

        firebaseRepository.addRoom(addressKey = key, fbRoom)

        return id

    }

    override suspend fun addRoom(addressKey: String?, room: Room): Boolean {

        Log.i(TAG, "Adding room with addressKey: $addressKey, room: $room")

        val key = getKey(addressKey)

        val result = cacheStorageInterface.addRoom(addressKey = key, room = room)

        val fbRoom = DataConverter.roomFirebase(room)

        firebaseRepository.addRoom(addressKey = key, fbRoom)

        return result
    }

    override suspend fun deleteRoom(addressKey: String?, roomId: Long): Boolean {

        Log.i(TAG, "Deleting room with addressKey: $addressKey, roomId: $roomId")

        val key = getKey(addressKey)

        cacheStorageInterface.deleteRoom(addressKey = key, roomId = roomId)

        firebaseRepository.deleteRoom(addressKey = key, roomId = roomId)

        return true

    }

    override suspend fun getRoomList(addressKey: String?): List<Room> {

        Log.i(TAG, "Getting room list with addressKey: $addressKey")

        val key = getKey(addressKey)

        val list = cacheStorageInterface.getRoomList(addressKey = key)

        if(list != null){

            CoroutineScope(Dispatchers.IO).launch {

                val fbList =DataConverter.roomListFirebase(firebaseRepository.getRoomList(key))

                repeat(fbList.size) {
                    cacheStorageInterface.addRoom(key, fbList[it])
                }

            }

            Log.i(TAG, "Got room list from CACHE $list")

            return list

        }else{

            val fbList =DataConverter.roomListFirebase(firebaseRepository.getRoomList(key))

            repeat(fbList.size) {
                cacheStorageInterface.addRoom(key, fbList[it])
            }

            Log.i(TAG, "Got room list from NET $fbList")

            return fbList
        }

    }

    override suspend fun getRoom(addressKey: String?, roomId: Long): Room {

        Log.i(TAG, "Getting room with addressKey: $addressKey , roomId: $roomId")

        val key = getKey(addressKey)

        val room = cacheStorageInterface.getRoom(addressKey = key, roomId = roomId)

        if(room != null){

            CoroutineScope(Dispatchers.IO).launch {
                val fbRoomList = DataConverter.roomListFirebase(firebaseRepository.getRoomList(key))

                repeat(fbRoomList.size) {

                    val roomTmp = fbRoomList[it]

                    if(roomTmp.id == roomId) {

                        cacheStorageInterface.addRoom(addressKey = key, room = roomTmp)
                    }
                }
            }

            return room

        }else{

            val fbRoomList = DataConverter.roomListFirebase(firebaseRepository.getRoomList(key))

            var roomFb:Room? = null

            repeat(fbRoomList.size) {

                val roomTmp = fbRoomList[it]

                if(roomTmp.id == roomId) {

                    roomFb = roomTmp

                    cacheStorageInterface.addRoom(addressKey = key, room = roomTmp)
                }

            }

            return roomFb?:throw Exception("There is no such room")
        }

    }

    override suspend fun addDeviceIdToRoom(
        addressKey: String?,
        deviceId: Long,
        roomId: Long
    ): Boolean {

        Log.i(TAG, "Adding deviceIdToRoom with addressKey: $addressKey, deviceId: $deviceId, roomId: $roomId")

        val key = getKey(addressKey)

        val room = getRoomFromCache(key, roomId)

        val list = if(room.devicesIdsInRoom!=null) ArrayList(room.devicesIdsInRoom!!) else ArrayList<Long>()

        list.add(deviceId)

        room.devicesIdsInRoom = list

        return addRoom(key, room)

    }

    override suspend fun removeDeviceId(
        addressKey: String?,
        deviceId: Long,
        roomId: Long
    ): Boolean {

        Log.i(TAG, "Removing deviceId from room with addressKey: $addressKey, deviceId: $deviceId, roomId: $roomId")

        val key = getKey(addressKey)

        val room = getRoomFromCache(key, roomId)

        val list = room.devicesIdsInRoom?.toMutableList()

        list?.remove(deviceId)

        room.devicesIdsInRoom = list

        return addRoom(key,room)

    }

    override suspend fun updateIcon(addressKey: String?, roomId: Long, icon: Int): Boolean {

        val key = getKey(addressKey)

        val room = getRoomFromCache(key, roomId)

        room.icon = icon

        return addRoom(key,room)

    }

    private suspend fun getRoomFromCache(addressKey: String?, id: Long): Room {
        Log.i(TAG, "Getting room from cache with addressKey: $addressKey , roomId: $id")

        val key = getKey(addressKey)

        val room = cacheStorageInterface.getRoom(addressKey = key, roomId = id)

        if(room != null){
            return room
        }else{
            throw Exception("There is no such room")
        }
    }

    override suspend fun addDevice(addressKey: String?, savingParams: SaveParamsDevice): Long {

        Log.i(TAG, "Adding device with addressKey: $addressKey, savingParams: $savingParams")

        val key = getKey(addressKey)

        val id = cacheStorageInterface.addDevice(addressKey = key, device = savingParams)

        val fbDevice = DataConverter.deviceFirebase(cacheStorageInterface.getDevice(key, id)!!)

        firebaseRepository.addDevice(addressKey = key, device = fbDevice)

        addDeviceIdToRoom(addressKey = key, deviceId = fbDevice.id, roomId = ALLDEVICES_ROOM_ID.toLong())

        return id

    }

    override suspend fun addDevice(addressKey: String?, device: Device): Boolean {

        Log.i(TAG, "Adding device with addressKey: $addressKey, device: $device")

        val key = getKey(addressKey)

        val result = cacheStorageInterface.addDevice(addressKey = key, device = device)

        val fbDevice = DataConverter.deviceFirebase(cacheStorageInterface.getDevice(key, device.id)!!)

        firebaseRepository.addDevice(addressKey = key, device = fbDevice)

        return result

    }

    override suspend fun getDevice(addressKey: String?, id: Long): Device {

        Log.i(TAG, "Getting device with addressKey: $addressKey, id: $id")

        val key = getKey(addressKey)

        val device = cacheStorageInterface.getDevice(addressKey = key, id = id)

        if(device != null){

            CoroutineScope(Dispatchers.IO).launch{
                val fbDeviceList = DataConverter.deviceListFirebase(firebaseRepository.getDevicesList(key))

                repeat(fbDeviceList.size) {

                    val deviceTmp = fbDeviceList[it]

                    if(deviceTmp.id == id) {
                        if(
                            device.id != deviceTmp.id ||
                            device.ip != deviceTmp.ip ||
                            device.name != deviceTmp.name ||
                            device.type != deviceTmp.type ||
                            device.settings != deviceTmp.settings
                        ){
                            cacheStorageInterface.addDevice(addressKey = key, device = deviceTmp)
                        }
                    }

                }
            }

            return device

        }else{

            val fbDeviceList = DataConverter.deviceListFirebase(firebaseRepository.getDevicesList(key))

            var deviceFb :Device? = null

            repeat(fbDeviceList.size) {

                val deviceTmp = fbDeviceList[it]

                if(deviceTmp.id == id) {

                    deviceFb = deviceTmp

                    cacheStorageInterface.addDevice(addressKey = key, device = deviceTmp)
                }

            }

            return deviceFb?:throw Exception("There is no such device with id $id")
        }

    }

    override suspend fun deleteDevice(addressKey: String?, id: Long): Boolean {

        Log.i(TAG, "Deleting device with addressKey: $addressKey, id: $id")

        val key = getKey(addressKey)

        val roomsList = getRoomListFromCache(addressKey = key)

        repeat(roomsList.size){

            val roomTmp = roomsList[it]

            val listTmp = ArrayList(roomTmp.devicesIdsInRoom ?: emptyList())

            //TODO("МОЖЕТ НЕ РАБОТАТЬ НАДО ТЕСТИТЬ")
            listTmp.remove(id)

            roomTmp.devicesIdsInRoom = listTmp

            addRoom(key, roomTmp)

        }

        cacheStorageInterface.deleteDevice(addressKey = key, id = id)

        firebaseRepository.deleteDevice(addressKey = key, deviceId = id)

        return true
    }

    override suspend fun updateSettings(
        addressKey: String?,
        settings: Settings,
        id: Long
    ): Boolean {

        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.settings = settings

        return addDevice(
            addressKey = key,
            device = device
        )

    }

    override suspend fun updateDeviceName(addressKey: String?, name: String, id: Long): Boolean {

        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.name = name

        return addDevice(
            addressKey = key,
            device = device
        )

    }

    override suspend fun updateDeviceIp(addressKey: String?, ip: String, id: Long): Boolean {

        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.ip = ip

        return addDevice(
            addressKey = key,
            device = device
        )

    }

    override suspend fun updateDeviceType(addressKey: String?, type: Int, id: Long): Boolean {

        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.type = type

        return addDevice(
            addressKey = key,
            device = device
        )

    }

    override suspend fun updateDeviceStatus(
        addressKey: String?,
        status: Boolean,
        id: Long
    ): Boolean {

        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.status = status

        return addDevice(
            addressKey = key,
            device = device
        )

    }

    override suspend fun setUpdatingStatus(
        addressKey: String?,
        isUpdating: Boolean,
        id: Long
    ): Boolean {
        val key = getKey(addressKey)

        val device = getDeviceFromCache(key, id)

        device.isUpdating = isUpdating

        return addDevice(
            addressKey = key,
            device = device
        )
    }

    //
    //
    //

    private suspend fun getRoomListFromCache(addressKey: String?): List<Room> {

        Log.i(TAG, "Getting room list FROM CACHE with addressKey: $addressKey")

        val key = getKey(addressKey)

        val list = cacheStorageInterface.getRoomList(addressKey = key) ?: throw Exception("There is no roomList")

            Log.i(TAG, "Got room list from CACHE $list")

            return list

    }

    private suspend fun getDeviceFromCache(addressKey: String?, id: Long): Device {
        Log.i(TAG, "Getting device from cache with addressKey: $addressKey, id: $id")

        val key = getKey(addressKey)

        val device = cacheStorageInterface.getDevice(addressKey = key, id = id)

        if (device != null) {
            return device
        } else {
            throw Exception("There is no such device")
        }
    }

    //
    // LIVE DATA
    //

    override fun getRoomListLiveData(addressKey: String?): LiveData<List<RoomEntityRoom>> {
        val key = getKey(addressKey)
        return cacheStorageInterface.getRoomListLiveData(key)
    }

    override fun getRoomLiveData(addressKey: String?, id: Long): LiveData<RoomEntityRoom> {
        val key = getKey(addressKey)
        return getRoomLiveData(key,id)
    }
    override fun getDeviceLiveData(addressKey: String?, id: Long): LiveData<RoomEntityDevice> {
        val key = getKey(addressKey)

        return cacheStorageInterface.getDeviceLiveData(addressKey = key, deviceId = id)
    }
}