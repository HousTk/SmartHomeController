package com.example.firebase

import com.example.data.data.repository.firebase.FirebaseAuthRepository
import com.example.data.data.repository.models.FirebaseAuthUser
import com.example.data.data.repository.models.FirebaseUser
import com.example.firebase.storage.auth.FirebaseDatabaseAuthStorageInterface
import com.example.firebase.storage.users.FirebaseDatabaseUsersStorageInterface

class FirebaseAuthImpl(
    private val firebaseDatabaseAuthStorageInterface: FirebaseDatabaseAuthStorageInterface,
    private val firebaseDatabaseUsersStorageInterface: FirebaseDatabaseUsersStorageInterface
):FirebaseAuthRepository {

    override fun isSignedIn(): Boolean {

        return firebaseDatabaseAuthStorageInterface.isSignedIn()

    }

    override suspend fun createAccount(email: String, password: String): FirebaseUser {

        firebaseDatabaseAuthStorageInterface.createAccount(email, password)

        val fbAuthUser = getCurrentUser()

        val fbUser = FirebaseUser(
            uid = fbAuthUser.uid,
            email = fbAuthUser.email,
            name = "",
            addressesKeys = ArrayList()
        )

        firebaseDatabaseUsersStorageInterface.addUser(fbUser)

        return fbUser

    }

    override suspend fun signIn(email: String, password: String): Boolean {

        return firebaseDatabaseAuthStorageInterface.signIn(email, password)

    }

    override fun getCurrentUser(): FirebaseAuthUser {

        return firebaseDatabaseAuthStorageInterface.getUser()

    }

    override fun signOut() {

        firebaseDatabaseAuthStorageInterface.signOut()

    }

}