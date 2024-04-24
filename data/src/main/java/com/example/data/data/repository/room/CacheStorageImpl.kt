package com.example.data.data.repository.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.data.repository.room.dao.RoomDAOAddresses
import com.example.data.data.repository.room.dao.RoomDAODevices
import com.example.data.data.repository.room.dao.RoomDAORooms
import com.example.data.data.repository.room.dao.RoomDAOUsers
import com.example.data.data.repository.room.databases.RoomDatabaseAddress
import com.example.data.data.repository.room.databases.RoomDatabaseMain
import com.example.data.data.repository.room.entity.RoomEntityAddress
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.data.data.repository.room.entity.RoomEntityUser
import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.main.User
import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.models.saveParams.SaveParamsRoom
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "CacheStorageImpl"

class CacheStorageImpl(
    private val context: Context,
) : CacheStorageInterface {

    private lateinit var mainDB: RoomDatabaseMain
    private lateinit var usersDAO: RoomDAOUsers

    private lateinit var addressDB: RoomDatabaseAddress
    private lateinit var addressInfoDAO: RoomDAOAddresses
    private lateinit var roomsDAO: RoomDAORooms
    private lateinit var devicesDAO: RoomDAODevices


    private var currentAddressKey: String? = null



    //
    // User
    //


    override suspend fun addUser(user: User): Boolean {

        usersDAO.addUser(
            RoomEntityUser(
                uid = user.uid,
                name = user.name,
                email = user.email,
                addressesKeys = user.addressesKeys
            )
        )

        return true

    }

    override suspend fun getAvailableAddressesKeysList(userUid: String): List<String> {


        return getUser(userUid)?.addressesKeys ?: throw Exception("There is no such user!")


    }

    override suspend fun addAvailableAddressKey(addressKey: String, userUid: String): Boolean {


        val user = getUser(userUid) ?: throw Exception("There is no such user!")

        val list = ArrayList(user.addressesKeys)

        list.add(addressKey)

        usersDAO.addUser(
            RoomEntityUser(
                uid = user.uid,
                name = user.name,
                email = user.email,
                addressesKeys = list
            )
        )

        return true


    }

    override suspend fun removeAvailableAddressKey(addressKey: String, userUid: String): Boolean {

        val user = getUser(userUid) ?: throw Exception("There is no such user!")

        val list = ArrayList(user.addressesKeys)

        list.remove(addressKey)

        usersDAO.addUser(
            RoomEntityUser(
                uid = user.uid,
                name = user.name,
                email = user.email,
                addressesKeys = list
            )
        )

        return true

    }


    override suspend fun getUser(userUid: String): User? {

        val user = usersDAO.getUser(userUid) ?: return null

        return User(
            uid = user.uid,
            name = user.name,
            email = user.email,
            addressesKeys = user.addressesKeys,
        )

    }

    //
    // ADDRESS
    //


    override suspend fun addAddress(address: Address): Boolean {

        actualizeDB(address.key)

        val roomAddress = RoomEntityAddress(
            key = address.key,
            name = address.name,
            wifiSSID = address.wifiSSID

        )
        addressInfoDAO.addAddress(roomAddress)

        return true

    }


    override suspend fun deleteAddress(addressKey: String): Boolean {

        actualizeDB(addressKey)

        deleteAllDevice(addressKey)

        deleteAllRooms(addressKey)

        addressInfoDAO.deleteAddress(key = addressKey)

        return true

    }

    override suspend fun getAddress(addressKey: String): Address? {

        actualizeDB(addressKey)

        val roomAddress = addressInfoDAO.getAddress(addressKey)
            ?: return null

        return Address(
            key = roomAddress.key,
            name = roomAddress.name,
            wifiSSID = roomAddress.wifiSSID
        )


    }


    //
    // ROOMS
    //


    override suspend fun addRoom(addressKey: String, room: SaveParamsRoom): Long {

        actualizeDB(addressKey)

        val roomEntity = RoomEntityRoom(
            name = room.name,
            icon = room.icon,
            devicesIdsInRoom = room.devicesIdsInRoom
        )

        return roomsDAO.addRoom(roomEntity)

    }

    override suspend fun addRoom(addressKey: String, room: Room): Boolean {

        actualizeDB(addressKey)

        val roomEntity = RoomEntityRoom(
            id = room.id,
            name = room.name,
            icon = room.icon,
            devicesIdsInRoom = room.devicesIdsInRoom
        )

        roomsDAO.addRoom(roomEntity)

        return true


    }

    override suspend fun deleteRoom(addressKey: String, roomId: Long): Boolean {

        actualizeDB(addressKey)

        roomsDAO.deleteRoom(roomId = roomId)

        return true

    }

    override suspend fun deleteAllRooms(addressKey: String): Boolean {

        actualizeDB(addressKey)

        roomsDAO.deleteAllRooms()

        return true
    }

    override suspend fun getRoomList(addressKey: String): List<Room>? {

        actualizeDB(addressKey)

        val list: ArrayList<Room> = ArrayList()

        val entityList = roomsDAO.getRoomList() ?: return null

        repeat(entityList.size) {

            val room = entityList[it]

            list.add(
                Room(
                    id = room.id!!,
                    name = room.name,
                    icon = room.icon,
                    devicesIdsInRoom = room.devicesIdsInRoom
                )
            )
        }

        return list

    }

    override suspend fun getRoom(addressKey: String, roomId: Long): Room? {

        actualizeDB(addressKey)

        val roomEntity = roomsDAO.getRoom(roomId) ?: return null


        return Room(
            id = roomEntity.id!!,
            name = roomEntity.name,
            icon = roomEntity.icon,
            devicesIdsInRoom = roomEntity.devicesIdsInRoom
        )


    }

    override fun getRoomListLiveData(addressKey: String): LiveData<List<RoomEntityRoom>> {
        actualizeDB(addressKey)
        return roomsDAO.getRoomListLiveData()
    }

    override fun getRoomLiveData(addressKey: String, id: Long): LiveData<RoomEntityRoom> {
        actualizeDB(addressKey)
        return roomsDAO.getRoomLiveData(roomId = id)
    }

    override fun getDeviceLiveData(addressKey: String, deviceId: Long): LiveData<RoomEntityDevice> {
        actualizeDB(addressKey)

        return devicesDAO.getDeviceLiveData(deviceId = deviceId)
    }


    //
    // DEVICES
    //


    override suspend fun addDevice(addressKey: String, device: SaveParamsDevice): Long {

        Log.i(TAG, "Saving device with addressKey $addressKey : $device")

        actualizeDB(addressKey)

        val deviceEntity = RoomEntityDevice(
            name = device.name,
            ip = device.ip,
            type = device.type,
            settings = Settings()
        )

        return devicesDAO.addDevice(deviceEntity)

    }

    override suspend fun addDevice(addressKey: String, device: Device): Boolean {

        Log.i(TAG, "Adding device with addressKey $addressKey : $device")

        actualizeDB(addressKey)

        val deviceEntity = RoomEntityDevice(
            id = device.id,
            name = device.name,
            ip = device.ip,
            type = device.type,
            status = device.status,
            isUpdating = device.isUpdating,
            settings = device.settings
        )

        devicesDAO.addDevice(deviceEntity)

        return true

    }

    override suspend fun deleteDevice(addressKey: String, id: Long): Boolean {

        actualizeDB(addressKey)

        devicesDAO.deleteDevice(deviceId = id)

        return true

    }

    override suspend fun deleteAllDevice(addressKey: String): Boolean {

        actualizeDB(addressKey)

        devicesDAO.deleteAllDevices()

        return true
    }

    override suspend fun getDevicesIdsList(addressKey: String): List<Long> {

        actualizeDB(addressKey)

        val roomEntity = roomsDAO.getRoom(ALLDEVICES_ROOM_ID.toLong()) ?: return emptyList()

        return roomEntity.devicesIdsInRoom ?: emptyList()

    }

    override suspend fun getDevice(addressKey: String, id: Long): Device? {

        Log.i(TAG, "Getting device with addressKey $addressKey")

        actualizeDB(addressKey)

        val device = devicesDAO.getDevice(deviceId = id) ?: return null

        Log.i(TAG, "Got device $device with addressKey $addressKey")

        return Device(
            id = device.id!!,
            name = device.name,
            ip = device.ip,
            type = device.type,
            status = device.status,
            isUpdating = device.isUpdating,
            settings = device.settings
        )
    }

    init {
        initMain()
    }


    private fun initMain() {

        val mainDbName = "Main"

        mainDB = androidx.room.Room.databaseBuilder(
            context,
            RoomDatabaseMain::class.java,
            mainDbName
        ).allowMainThreadQueries()
            .build()

        usersDAO = mainDB.usersDao()

    }

    private fun initAddress(addressKey: String) {

        val dbName = "Address$addressKey"

        addressDB = androidx.room.Room.databaseBuilder(
            context,
            RoomDatabaseAddress::class.java,
            dbName
        ).allowMainThreadQueries()
            .build()

        addressInfoDAO = addressDB.addressInfoDao()
        roomsDAO = addressDB.roomsDao()
        devicesDAO = addressDB.devicesDao()

    }

    private fun actualizeDB(addressKey: String){

        if(addressKey == currentAddressKey) return

        initAddress(addressKey)

        currentAddressKey = addressKey

    }
}