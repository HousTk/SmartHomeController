package com.example.domain.domain.usecase.user

import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.repository.UserRepository


class GetAvailableAddressesKeysUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun execute():List<String>{

        val userUid =authRepository.getCurrentUser().uid

        return userRepository.getAvailableAddressesKeysFromNet(userUid)

    }

}