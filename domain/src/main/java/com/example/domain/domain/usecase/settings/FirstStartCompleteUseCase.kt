package com.example.domain.domain.usecase.settings

import com.example.domain.domain.repository.SettingsRepository

class FirstStartCompleteUseCase(

    private val settingsRepository: SettingsRepository

) {

    fun execute(){
        settingsRepository.firstStartComplete()
    }

}