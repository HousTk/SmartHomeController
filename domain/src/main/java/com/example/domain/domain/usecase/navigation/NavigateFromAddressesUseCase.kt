package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromAddressesUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val dataLoadingFragmentId: Int,
    private val addAddressFragmentId:Int
) {

    fun toDataLoading(){
        navigationControllerInterface.navigateTo(dataLoadingFragmentId)
        navigationControllerInterface.setStartDestination(dataLoadingFragmentId)
    }

    fun toAddAddress(){
        navigationControllerInterface.navigateTo(addAddressFragmentId)
    }

}