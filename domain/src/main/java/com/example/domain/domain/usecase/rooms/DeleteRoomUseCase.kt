package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.AddressRepository


class DeleteRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(roomId:Long):Boolean{

        addressRepository.deleteRoom(addressKey = null, roomId =roomId)

        return true

    }

}