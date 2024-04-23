package com.example.domain.domain.navController

interface NavigationControllerInterface {
    fun navigateTo(destinationPageId : Int)

    fun navigateBack()

    fun navigateWithArgs(key:String, args: Any, destinationPageId: Int)

    fun navigateBackTo(destinationPageId: Int)
}