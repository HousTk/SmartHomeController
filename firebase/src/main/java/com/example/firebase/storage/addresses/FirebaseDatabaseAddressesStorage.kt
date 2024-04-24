package com.example.firebase.storage.addresses

import com.example.data.data.repository.models.FirebaseAddress
import com.example.data.data.repository.models.FirebaseAddressSaveParams
import com.example.firebase.utils.ADDRESSES_KEY
import com.example.firebase.utils.DATABASE_URL
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class FirebaseDatabaseAddressesStorage(): FirebaseDatabaseAddressesStorageInterface {

    private val database =
        Firebase.database(DATABASE_URL)
            .reference
            .child(ADDRESSES_KEY)



    override suspend fun get(addressKey: String):Any? {

        return database.child(addressKey).get().await().value


    }

    override suspend fun saveAddressAndGetKey(address: FirebaseAddressSaveParams): String? {

        val key = database.push().key ?: return null

        val saveAddress = FirebaseAddress(
            key =  key,
            name = address.name,
            wifiSSID = address.wifiSSID
        )

        database.child(key).setValue(saveAddress).await()

        return key



    }

    override suspend fun deleteAddress(addressKey: String): Boolean {

        database.child(addressKey).setValue(null).await()

        return true

    }


}