package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.models.main.Room
import com.example.domain.domain.repository.AddressRepository


class GetRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(roomId:Long):Room {

         return addressRepository.getRoom(addressKey = null, roomId = roomId)
    }

}