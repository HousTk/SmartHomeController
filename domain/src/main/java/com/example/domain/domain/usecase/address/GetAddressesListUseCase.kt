package com.example.domain.domain.usecase.address

import com.example.domain.domain.models.address.Address
import com.example.domain.domain.repository.AddressRepository

class GetAddressesListUseCase(
    private val addressRepository: AddressRepository
) {

    fun execute():ArrayList<Address>{
        return addressRepository.getAddressList()
    }

}