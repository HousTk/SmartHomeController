package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom

import androidx.lifecycle.ViewModel
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class AddDeviceToRoomFirstPageViewModel(
    private val navigationController: NavigationController
) : ViewModel() {

    fun onClickAddDevice(currentRoom: Int) {
        navigationController.navigateWithArgs(
            key = "CurrentRoom",
            args = currentRoom,
            destinationPageId = R.id.addDeviceToRoomFragment
        )
    }

    fun onClickAddNewDevice(currentRoom: Int) {
        navigationController.navigateWithArgs(
            key = "CurrentRoom",
            args = currentRoom,
            destinationPageId = R.id.addNewDeviceFragment
        )
    }
}