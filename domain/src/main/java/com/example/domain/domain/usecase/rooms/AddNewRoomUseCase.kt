package com.example.domain.domain.usecase.rooms


import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.saveParams.SaveParamsRoom
import com.example.domain.domain.repository.AddressRepository

class AddNewRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(room:SaveParamsRoom):Long{

        return addressRepository.addRoom(addressKey = null, room = room)

    }

}