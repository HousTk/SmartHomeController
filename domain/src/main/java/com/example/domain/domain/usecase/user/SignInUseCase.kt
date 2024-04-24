package com.example.domain.domain.usecase.user

import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.repository.UserRepository
class SignInUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend fun execute(
        email:String,
        password:String
    ):Boolean{

        if(authRepository.signIn(email, password)){

            val userUid = authRepository.getCurrentUser().uid

            val user = userRepository.getUser(userUid)

            return userRepository.addUser(user)

        }else{
            return false
        }

    }

}