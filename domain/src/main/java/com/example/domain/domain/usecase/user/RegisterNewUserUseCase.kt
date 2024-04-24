package com.example.domain.domain.usecase.user

import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.repository.UserRepository

class RegisterNewUserUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun execute(email:String, password:String):Boolean

    {

        authRepository.createAccount(email, password)

        val userUid = authRepository.getCurrentUser().uid

        userRepository.getUser(userUid)

        return true
    }

}