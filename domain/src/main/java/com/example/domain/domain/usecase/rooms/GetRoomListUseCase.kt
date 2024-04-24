package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.models.main.Room
import com.example.domain.domain.repository.AddressRepository

class GetRoomListUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute():List<Room>{

        return addressRepository.getRoomList(addressKey = null)
    }

}