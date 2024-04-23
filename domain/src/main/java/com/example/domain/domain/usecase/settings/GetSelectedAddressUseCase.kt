package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository

class GetSelectedAddressUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute():Int{
        return settingsRepository.getSelectedAddress()
    }

}