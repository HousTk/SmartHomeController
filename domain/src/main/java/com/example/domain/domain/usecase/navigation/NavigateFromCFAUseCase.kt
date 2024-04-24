package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromCFAUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val homeFragmentId:Int
) {

    fun execute(){
        navigationControllerInterface.navigateTo(homeFragmentId)
        navigationControllerInterface.setStartDestination(homeFragmentId)
    }

}