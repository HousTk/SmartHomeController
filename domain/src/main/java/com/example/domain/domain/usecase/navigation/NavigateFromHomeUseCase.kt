package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromHomeUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val addressesFragmentId: Int,
    private val addRoomFragmentId: Int,
    private val addDeviceToRoomFragmentId: Int,
    private val deviceFragmentId: Int,
    private val keyDeviceId:String,
    private val keyRoomPosition:String,
    private val profileFragmentId: Int
) {

    fun toAddresses(){
        navigationControllerInterface.navigateTo(addressesFragmentId)
    }

    fun toAddRoom(){
        navigationControllerInterface.navigateTo(addRoomFragmentId)
    }


    fun toAddDevice(roomId:Long){

        navigationControllerInterface.navigateWithArgs(
            key = "CurrentRoom",
            args = roomId,
            destinationPageId = addDeviceToRoomFragmentId
        )

    }

    fun toDevice(
        deviceId:Long,
        roomId:Long
    ){

        navigationControllerInterface.putArgsToBundle(
            bundleKey = "ToDevice",
            argKey = keyDeviceId,
            arg = deviceId
        )

        navigationControllerInterface.putArgsToBundle(
            bundleKey = "ToDevice",
            argKey = keyRoomPosition,
            arg = roomId
        )

        navigationControllerInterface.navigateToWithBundle(
            bundleKey = "ToDevice",
            deviceFragmentId
        )

    }

    fun toProfile(){
        navigationControllerInterface.navigateTo(profileFragmentId)
    }
}