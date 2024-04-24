package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromLogUpUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val dataLoadingFragmentId:Int
) {

    fun toHome(){

        with(navigationControllerInterface){
            navigateTo(dataLoadingFragmentId)
            setStartDestination(dataLoadingFragmentId)
        }

    }


}