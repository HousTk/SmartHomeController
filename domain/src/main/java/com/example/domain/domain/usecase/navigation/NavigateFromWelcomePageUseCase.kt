package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromWelcomePageUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val logInFragmentId: Int,
    private val logUpFragmentId: Int
) {

    fun toLogIn(){
        navigationControllerInterface.navigateTo(logInFragmentId)
    }

    fun toLogUp(){
        navigationControllerInterface.navigateTo(logUpFragmentId)
    }

}