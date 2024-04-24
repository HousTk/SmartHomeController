package com.example.firebase

import com.example.data.data.repository.firebase.FirebaseRepository
import com.example.data.data.repository.models.FirebaseAddress
import com.example.data.data.repository.models.FirebaseAddressSaveParams
import com.example.data.data.repository.models.FirebaseDevice
import com.example.data.data.repository.models.FirebaseRoom
import com.example.data.data.repository.models.FirebaseUser
import com.example.data.data.repository.utils.DataConverter
import com.example.firebase.storage.addresses.FirebaseDatabaseAddressesStorageInterface
import com.example.firebase.storage.auth.FirebaseDatabaseAuthStorageInterface
import com.example.firebase.storage.devices.FirebaseDatabaseDevicesStorageInterface
import com.example.firebase.storage.rooms.FirebaseDatabaseRoomsStorageInterface
import com.example.firebase.storage.settings.FirebaseDatabaseSettingsStorageInterface
import com.example.firebase.storage.users.FirebaseDatabaseUsersStorageInterface

class FirebaseImpl(
    private val firebaseDatabaseDevicesStorageInterface: FirebaseDatabaseDevicesStorageInterface,
    private val firebaseDatabaseRoomsStorageInterface: FirebaseDatabaseRoomsStorageInterface,
    private val firebaseDatabaseAddressesStorageInterface: FirebaseDatabaseAddressesStorageInterface,
    private val firebaseDatabaseSettingsStorageInterface: FirebaseDatabaseSettingsStorageInterface,
    private val firebaseDatabaseUsersStorageInterface: FirebaseDatabaseUsersStorageInterface,
    private val firebaseDatabaseAuthStorageInterface: FirebaseDatabaseAuthStorageInterface,
):FirebaseRepository {



    override suspend fun getSettings(): HashMap<String, String> {

        return firebaseDatabaseSettingsStorageInterface.get() as HashMap<String, String>

    }

    override suspend fun addUser(user: FirebaseUser) {
        firebaseDatabaseUsersStorageInterface.addUser(user)
    }

    override suspend fun addAvailableAddressKey(addressKey: String, userUid: String) {
        firebaseDatabaseUsersStorageInterface.addAvailableAddress(userUid = userUid, addressKey = addressKey)
    }

    override suspend fun getUser(userUid: String): FirebaseUser {

        val data = firebaseDatabaseUsersStorageInterface.getUser(userUid) as HashMap<*, *>

        return FirebaseUser(
            uid = data["uid"] as String,
            name = data["name"] as String,
            email = data["email"] as String,
            addressesKeys = if (data["addressesKeys"] != null) data["addressesKeys"] as ArrayList<String> else ArrayList()
        )



    }

    override suspend fun saveAddress(address: FirebaseAddressSaveParams): String? {

        return firebaseDatabaseAddressesStorageInterface.saveAddressAndGetKey(address = address)

    }

    override suspend fun getAddress(addressKey: String): FirebaseAddress {

        val fbAddress = firebaseDatabaseAddressesStorageInterface.get(addressKey)

        return Parser.parseAddress(fbAddress)

    }

    override suspend fun getRoomList(addressKey: String): List<FirebaseRoom> {

        val fbList = firebaseDatabaseAddressesStorageInterface.get(addressKey)

        return Parser.parseRoomsList(fbList)

    }

    override suspend fun getDevicesList(addressKey: String): List<FirebaseDevice> {

        val fbList = firebaseDatabaseAddressesStorageInterface.get(addressKey)

        return Parser.parseDevicesList(fbList)

    }

    override suspend fun addDevice(addressKey: String, device: FirebaseDevice): Boolean {

        return firebaseDatabaseDevicesStorageInterface.saveDevice(
            device = device,
            addressKey = addressKey
        )

    }

    override suspend fun addRoom(addressKey: String, room: FirebaseRoom): Boolean {

        return firebaseDatabaseRoomsStorageInterface.saveRoom(
            room = room,
            addressKey = addressKey
        )

    }

    override suspend fun deleteAddress(addressKey: String): Boolean {

        return firebaseDatabaseAddressesStorageInterface.deleteAddress(addressKey)

    }

    override suspend fun deleteRoom(addressKey: String, roomId: Long): Boolean {

        return firebaseDatabaseRoomsStorageInterface.deleteRoom(
            roomId = roomId,
            addressKey = addressKey
        )

    }

    override suspend fun deleteDevice(
        addressKey: String,
        deviceId: Long,
    ): Boolean {


        return firebaseDatabaseDevicesStorageInterface.deleteDevice(
            deviceId = deviceId,
            addressKey = addressKey
        )

    }

}