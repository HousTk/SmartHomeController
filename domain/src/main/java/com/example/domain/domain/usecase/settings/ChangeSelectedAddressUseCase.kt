package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository

class ChangeSelectedAddressUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute(selectedAddress: Int){
        settingsRepository.changeSelectedAddress(selectedAddress)
    }

}