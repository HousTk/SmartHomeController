package com.example.data.data.repository

import com.example.data.data.repository.firebase.FirebaseRepository
import com.example.data.data.repository.room.CacheStorageInterface
import com.example.data.data.repository.utils.DataConverter
import com.example.domain.domain.models.main.User
import com.example.domain.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val cacheStorageInterface: CacheStorageInterface,
    private val firebaseRepository: FirebaseRepository
):UserRepository {

    override suspend fun addUser(user: User): Boolean {

        cacheStorageInterface.addUser(user)

        firebaseRepository.addUser(DataConverter.userFirebase(user))

        return true

    }

    override suspend fun getUser(userUid: String): User {

        val user = cacheStorageInterface.getUser(userUid = userUid)

        if(user != null) {

            CoroutineScope(Dispatchers.IO).launch {

                val fbUser = DataConverter.userFirebase( firebaseRepository.getUser(userUid = userUid) )

                cacheStorageInterface.addUser(fbUser)

            }

            return user

        }
        else{

            val fbUser = DataConverter.userFirebase( firebaseRepository.getUser(userUid = userUid) )

            cacheStorageInterface.addUser(fbUser)

            return fbUser

        }

    }

    override suspend fun addAvailableAddressKey(addressKey: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableAddressesKeysFromNet(userUid: String): List<String> {

        val fbUser = DataConverter.userFirebase(firebaseRepository.getUser(userUid = userUid))

        val keys = fbUser.addressesKeys

        return keys

    }

    override suspend fun setAvailableAddressesKeys(userUid: String, keys: List<String>): Boolean {

        val user = firebaseRepository.getUser(userUid)

        user.addressesKeys = keys

        firebaseRepository.addUser(user)

        return true
    }
}