package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.RoomRepository

class AddDeviceToRoomUseCase(
    private val roomRepository: RoomRepository
) {

    fun execute(deviceId:Int, roomPosition:Int):Boolean{

        return roomRepository.addDeviceIdToRoom(
            deviceId = deviceId,
            roomPosition = roomPosition
        )

    }

}