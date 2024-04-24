package com.example.domain.domain.usecase.user

import com.example.domain.domain.repository.AuthRepository


class IsSignedInUseCase(
    private val authRepository: AuthRepository
) {

    fun execute():Boolean{
        return authRepository.isSignedIn()

    }

}