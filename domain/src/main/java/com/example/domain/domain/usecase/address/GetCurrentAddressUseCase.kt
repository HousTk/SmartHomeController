package com.example.domain.domain.usecase.address

import com.example.domain.domain.models.address.Address
import com.example.domain.domain.repository.AddressRepository

class GetCurrentAddressUseCase(
    private val addressRepository: AddressRepository
) {

    fun execute():Address{

        return addressRepository.getCurrentAddress()

    }

}