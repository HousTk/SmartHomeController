package com.example.domain.domain.usecase.address


import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.SettingsRepository


class RemoveAddressUseCase(
    private val addressRepository: AddressRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend fun execute(addressKey: String): Boolean {

        val currentAddress = settingsRepository.getSelectedAddressKey()

        if (currentAddress == addressKey) throw Exception("can't delete selected address. WIP ")

        return addressRepository.deleteAddress(addressKey = addressKey)

    }

}