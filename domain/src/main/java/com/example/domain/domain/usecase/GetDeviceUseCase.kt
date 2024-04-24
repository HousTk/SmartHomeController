package com.example.domain.domain.usecase

import com.example.domain.domain.models.main.Device
import com.example.domain.domain.repository.AddressRepository


class GetDeviceUseCase(
    private val addressRepository: AddressRepository
) {
    suspend fun execute(id:Long):Device
    {

        return addressRepository.getDevice(addressKey = null, id)

    }
}