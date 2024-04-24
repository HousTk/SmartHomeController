package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromADTRUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val homeFragmentId:Int
) {

    fun back(){
        navigationControllerInterface.navigateBackTo(homeFragmentId)
    }

}