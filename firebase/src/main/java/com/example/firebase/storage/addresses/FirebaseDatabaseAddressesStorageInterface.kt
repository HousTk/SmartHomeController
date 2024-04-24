package com.example.firebase.storage.addresses

import com.example.data.data.repository.models.FirebaseAddressSaveParams


interface FirebaseDatabaseAddressesStorageInterface {

    suspend fun get(addressKey:String):Any?

    suspend fun saveAddressAndGetKey(address: FirebaseAddressSaveParams):String?

    suspend fun deleteAddress(addressKey: String):Boolean

}