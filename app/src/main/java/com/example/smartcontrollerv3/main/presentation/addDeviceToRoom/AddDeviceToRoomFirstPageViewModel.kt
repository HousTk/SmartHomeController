package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom

import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.navigation.NavigateFromADTRFPUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class AddDeviceToRoomFirstPageViewModel(
    private val navigateFromADTRFPUseCase: NavigateFromADTRFPUseCase,
) : ViewModel() {

    private var currentRoomId: Long = -1

    fun initVM(roomId: Long) {

        if (roomId == ALLDEVICES_ROOM_ID.toLong()) {
            navigateFromADTRFPUseCase.toAddNewDevice(roomId = roomId)
        } else {
            currentRoomId = roomId
        }

    }

    fun onClickAddDevice() {
        navigateFromADTRFPUseCase.toAddDeviceToRoom(roomId = currentRoomId)
    }

    fun onClickAddNewDevice() {
        navigateFromADTRFPUseCase.toAddNewDevice(roomId = currentRoomId)
    }
}