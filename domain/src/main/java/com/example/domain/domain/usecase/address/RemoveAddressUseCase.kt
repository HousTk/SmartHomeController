package com.example.domain.domain.usecase.address

import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.SettingsRepository

class RemoveAddressUseCase(
    private val addressRepository: AddressRepository,
    private val settingsRepository: SettingsRepository
) {

    fun execute(addressPosition:Int):Boolean{

        val addressesList = addressRepository.getAddressList()

        if (addressPosition == addressesList.lastIndex){
            settingsRepository.changeSelectedAddress(addressesList.lastIndex - 1)
        }

        return addressRepository.removeAddress(addressPosition)

    }

}