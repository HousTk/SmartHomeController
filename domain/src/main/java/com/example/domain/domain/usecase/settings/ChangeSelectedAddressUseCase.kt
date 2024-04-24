package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository


class ChangeSelectedAddressUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend fun execute(selectedAddressKey: String){
        settingsRepository.changeSelectedAddressKey(selectedAddressKey = selectedAddressKey)

    }

}