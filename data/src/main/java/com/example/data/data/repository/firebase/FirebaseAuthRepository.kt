package com.example.data.data.repository.firebase

import com.example.data.data.repository.models.FirebaseAuthUser
import com.example.data.data.repository.models.FirebaseUser

interface FirebaseAuthRepository {

    fun isSignedIn(): Boolean

    suspend fun createAccount(
        email:String,
        password:String
    ): FirebaseUser

    suspend fun signIn(
        email:String,
        password:String
    ): Boolean

    fun getCurrentUser(): FirebaseAuthUser

    fun signOut()

}