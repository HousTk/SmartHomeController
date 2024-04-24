package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.AddressRepository

class AddDeviceToRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(deviceId:Long, roomId:Long):Boolean{

        addressRepository.addDeviceIdToRoom(addressKey = null, deviceId = deviceId, roomId = roomId)

        return true
    }

    suspend fun list(list: List<Long>, roomId: Long): Boolean {

        val room = addressRepository.getRoom(addressKey = null, roomId = roomId)

        val newList = room.devicesIdsInRoom?.plus(list) ?: list

        room.devicesIdsInRoom = newList

        addressRepository.addRoom(addressKey = null, room = room)

        return true
    }

}