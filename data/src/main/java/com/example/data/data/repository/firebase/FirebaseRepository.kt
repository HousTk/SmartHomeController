package com.example.data.data.repository.firebase

import com.example.data.data.repository.models.FirebaseAddress
import com.example.data.data.repository.models.FirebaseAddressSaveParams
import com.example.data.data.repository.models.FirebaseAuthUser
import com.example.data.data.repository.models.FirebaseDevice
import com.example.data.data.repository.models.FirebaseRoom
import com.example.data.data.repository.models.FirebaseUser

interface FirebaseRepository {

    suspend fun getSettings():HashMap<String, String>

    //

    suspend fun addUser(user:FirebaseUser)

    suspend fun addAvailableAddressKey(addressKey: String, userUid: String)

    suspend fun getUser(userUid:String): FirebaseUser

    //

    suspend fun saveAddress(address:FirebaseAddressSaveParams):String?

    suspend fun getAddress(addressKey: String): FirebaseAddress

    suspend fun deleteAddress(addressKey: String):Boolean

    //

    suspend fun getRoomList(addressKey: String):List<FirebaseRoom>

    suspend fun addRoom(addressKey: String, room: FirebaseRoom):Boolean

    suspend fun deleteRoom(addressKey: String, roomId:Long):Boolean

    //

    suspend fun getDevicesList(addressKey: String):List<FirebaseDevice>

    suspend fun addDevice(addressKey: String, device: FirebaseDevice):Boolean

    suspend fun deleteDevice(addressKey: String, deviceId:Long):Boolean

}