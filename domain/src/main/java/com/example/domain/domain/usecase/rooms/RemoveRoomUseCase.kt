package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.RoomRepository

class RemoveRoomUseCase(
    private val roomRepository: RoomRepository
) {

    fun execute(roomPosition:Int):Boolean{

        return roomRepository.removeFromList(roomPosition = roomPosition)

    }

}