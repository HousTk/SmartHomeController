package com.example.firebase.storage.auth

import com.example.data.data.repository.models.FirebaseAuthUser

interface FirebaseDatabaseAuthStorageInterface {

    fun isSignedIn(): Boolean

    suspend fun createAccount(email:String, password:String):Boolean

    suspend fun signIn(email: String, password: String):Boolean

    fun getUser(): FirebaseAuthUser

    fun signOut()
}