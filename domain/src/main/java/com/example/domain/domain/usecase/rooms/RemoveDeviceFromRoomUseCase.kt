package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.RoomRepository

class RemoveDeviceFromRoomUseCase(
    private val roomRepository: RoomRepository
) {

    fun execute(deviceId:Int, roomPosition:Int):Boolean{

        return roomRepository.removeDeviceId(
            deviceId = deviceId,
            roomPosition = roomPosition
        )

    }

}