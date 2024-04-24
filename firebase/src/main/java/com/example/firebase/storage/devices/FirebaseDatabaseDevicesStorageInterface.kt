package com.example.firebase.storage.devices

import com.example.data.data.repository.models.FirebaseDevice

interface FirebaseDatabaseDevicesStorageInterface {


    suspend fun get(addressKey:String):Any?

    suspend fun saveDevice(device: FirebaseDevice, addressKey:String):Boolean

    suspend fun deleteDevice( deviceId:Long, addressKey:String):Boolean


}