package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromLogIn(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val dataLoadingFragmentId:Int
) {


    fun execute(){

        navigationControllerInterface.navigateTo(dataLoadingFragmentId)
        navigationControllerInterface.setStartDestination(dataLoadingFragmentId)

    }


}