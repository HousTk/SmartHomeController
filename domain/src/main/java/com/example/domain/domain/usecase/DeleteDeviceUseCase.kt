package com.example.domain.domain.usecase

import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.repository.IdRepository
import com.example.domain.domain.repository.RoomRepository

class DeleteDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    private val idRepository: IdRepository,
    private val roomRepository: RoomRepository
) {

    fun execute(id: Int): Boolean {

        val roomList = roomRepository.getRoomList()

        repeat(roomList.size){roomPosition ->
            val room = roomList[roomPosition]
            repeat(room.devicesIdsInRoom.size){devicePosition ->
                if(room.devicesIdsInRoom[devicePosition] == id){

                    if(!roomRepository.removeDeviceId(id, roomPosition)){
                        return false
                    }
                }
            }
        }

        val deviceResult = deviceRepository.deleteDevice(id)
        val idResult = idRepository.removeFromList(id)

        if (deviceResult && idResult) {
            return true
        } else {
            return false
        }
    }
}