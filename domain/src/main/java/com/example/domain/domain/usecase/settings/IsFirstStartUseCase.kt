package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository

class IsFirstStartUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun execute():Boolean{
        return settingsRepository.isFirstStart()
    }

}