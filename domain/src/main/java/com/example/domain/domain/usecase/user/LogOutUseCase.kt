package com.example.domain.domain.usecase.user

import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.repository.AuthRepository

class LogOutUseCase(
    private val authRepository: AuthRepository,
    private val navigationControllerInterface: NavigationControllerInterface,
    private val welcomePageFragmentId:Int
) {

    fun execute(){

        authRepository.signOut()
        navigationControllerInterface.navigateTo(welcomePageFragmentId)
        navigationControllerInterface.setStartDestination(welcomePageFragmentId)

    }

}