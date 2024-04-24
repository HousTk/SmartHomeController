package com.example.domain.domain.repository

import com.example.domain.domain.models.main.User

interface AuthRepository {

    fun isSignedIn(): Boolean

    suspend fun createAccount(
        email:String,
        password:String
    ): Boolean

    suspend fun signIn(
        email:String,
        password:String
    ): Boolean

    suspend fun getCurrentUser(): User

    fun signOut()

}