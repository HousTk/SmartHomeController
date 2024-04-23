package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.models.room.Room
import com.example.domain.domain.repository.RoomRepository

class AddNewRoomUseCase(
    private val roomRepository: RoomRepository
) {

    fun execute(roomName:String, icon:Int, deviceIdsArrayList:ArrayList<Int> = ArrayList()):Boolean{
        val room = Room(
            name = roomName,
            icon = icon,
            devicesIdsInRoom = deviceIdsArrayList
            )
        return roomRepository.addToList(room)

    }

}