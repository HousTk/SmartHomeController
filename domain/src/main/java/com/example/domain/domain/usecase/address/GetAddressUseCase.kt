package com.example.domain.domain.usecase.address

import com.example.domain.domain.models.main.Address
import com.example.domain.domain.repository.AddressRepository

class GetAddressUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(addressKey:String):Address{
        return addressRepository.getAddress(addressKey)
    }

}