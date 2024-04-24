package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository


class GetSelectedAddressKeyUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute():String{
        return settingsRepository.getSelectedAddressKey()
    }

}