package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.models.room.Room
import com.example.domain.domain.repository.RoomRepository

class GetRoomListUseCase(
    private val roomRepository: RoomRepository
) {

    fun execute():ArrayList<Room>{
        return roomRepository.getRoomList()
    }

}