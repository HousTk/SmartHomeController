package com.example.domain.domain.repository

import com.example.domain.domain.models.main.User

interface UserRepository {


    suspend fun addUser(user: User):Boolean

    suspend fun getUser(userUid: String): User

    suspend fun addAvailableAddressKey(addressKey:String):Boolean

    suspend fun getAvailableAddressesKeysFromNet(userUid: String):List<String>

    suspend fun setAvailableAddressesKeys(userUid: String, keys:List<String>):Boolean
}