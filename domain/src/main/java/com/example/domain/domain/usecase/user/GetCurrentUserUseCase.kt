package com.example.domain.domain.usecase.user


import com.example.domain.domain.models.main.User
import com.example.domain.domain.repository.AuthRepository

import com.example.domain.domain.repository.UserRepository

class GetCurrentUserUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun execute(): User {

        val uid = authRepository.getCurrentUser().uid
        return userRepository.getUser(uid)

    }

}