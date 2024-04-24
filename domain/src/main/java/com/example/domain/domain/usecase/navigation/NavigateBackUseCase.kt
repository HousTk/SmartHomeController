package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateBackUseCase(private val navigationControllerInterface: NavigationControllerInterface) {

    fun execute() {
        navigationControllerInterface.navigateBack()
    }

}