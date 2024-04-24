package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromDataLoadingUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val homeFragmentId:Int,
    private val createFirstAddressFragmentId:Int
) {

    fun toHome(){
        navigationControllerInterface.navigateTo(homeFragmentId)
        navigationControllerInterface.setStartDestination(homeFragmentId)
    }

    fun toCFA(){
        navigationControllerInterface.navigateTo(createFirstAddressFragmentId)
        navigationControllerInterface.setStartDestination(createFirstAddressFragmentId)
    }

}