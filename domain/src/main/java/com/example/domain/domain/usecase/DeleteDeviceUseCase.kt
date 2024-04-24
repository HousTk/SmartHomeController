package com.example.domain.domain.usecase

import com.example.domain.domain.repository.AddressRepository


class DeleteDeviceUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(id: Long):Boolean {

        return addressRepository.deleteDevice(addressKey = null, id = id)


    }
}