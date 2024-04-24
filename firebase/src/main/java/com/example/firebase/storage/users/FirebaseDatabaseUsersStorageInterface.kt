package com.example.firebase.storage.users

import com.example.data.data.repository.models.FirebaseUser


interface FirebaseDatabaseUsersStorageInterface {

    suspend fun addUser(user: FirebaseUser)

    suspend fun getUser(userUid:String):Any?

    suspend fun addAvailableAddress(userUid: String, addressKey: String)

    suspend fun removeAddress(userUid: String, addressKey: String)

}