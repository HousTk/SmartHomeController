package com.example.data.data.repository

import com.example.data.data.repository.firebase.FirebaseAuthRepository
import com.example.data.data.repository.firebase.FirebaseRepository
import com.example.data.data.repository.utils.DataConverter
import com.example.domain.domain.models.main.User
import com.example.domain.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseRepository: FirebaseRepository
):AuthRepository {

    override fun isSignedIn(): Boolean {

        return firebaseAuthRepository.isSignedIn()

    }

    override suspend fun createAccount(email: String, password: String): Boolean {

        firebaseAuthRepository.createAccount(email, password)

        return true
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return firebaseAuthRepository.signIn(email, password)
    }

    override suspend fun getCurrentUser(): User {

        val authUser = firebaseAuthRepository.getCurrentUser()

        return DataConverter.userFirebase( firebaseRepository.getUser(authUser.uid) )

    }

    override fun signOut() {
       firebaseAuthRepository.signOut()
    }

}