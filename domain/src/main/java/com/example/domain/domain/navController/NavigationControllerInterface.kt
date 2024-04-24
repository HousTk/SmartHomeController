package com.example.domain.domain.navController

interface NavigationControllerInterface {
    fun navigateTo(destinationPageId : Int)

    fun navigateBack()

    fun navigateWithArgs(key:String, args: Any, destinationPageId: Int)

    fun navigateBackTo(destinationPageId: Int)

    fun setStartDestination(startPageId: Int)

    fun putArgsToBundle(bundleKey:String, argKey: String, arg: Any):Boolean

    fun navigateToWithBundle(bundleKey:String, destinationPageId: Int)
}