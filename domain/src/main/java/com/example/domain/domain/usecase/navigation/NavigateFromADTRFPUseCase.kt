package com.example.domain.domain.usecase.navigation

import com.example.domain.domain.navController.NavigationControllerInterface

class NavigateFromADTRFPUseCase(
    private val navigationControllerInterface: NavigationControllerInterface,
    private val addNewDeviceFragmentId:Int,
    private val addDeviceToRoomFragmentId:Int
) {

    fun toAddNewDevice(roomId:Long){
        navigationControllerInterface.navigateWithArgs(
            key = "CurrentRoom",
            args = roomId,
            destinationPageId = addNewDeviceFragmentId
        )
    }

    fun toAddDeviceToRoom(roomId: Long){
        navigationControllerInterface.navigateWithArgs(
            key = "CurrentRoom",
            args = roomId,
            destinationPageId = addDeviceToRoomFragmentId
        )
    }
}