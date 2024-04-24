package com.example.domain.domain.usecase.rooms

import com.example.domain.domain.repository.AddressRepository


class RemoveDeviceFromRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(deviceId:Long, roomId:Long):Boolean{

        return addressRepository.removeDeviceId(addressKey = null, deviceId = deviceId, roomId = roomId)

    }

}