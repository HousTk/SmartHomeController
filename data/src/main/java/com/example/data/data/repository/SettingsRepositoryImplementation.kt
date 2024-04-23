package com.example.data.data.repository

import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.repository.SettingsRepository

class SettingsRepositoryImplementation(

    private val settingsStorageInterface: SettingsStorageInterface

):SettingsRepository {

    override fun changeSelectedAddress(selectedAddress: Int): Boolean {
        return settingsStorageInterface.changeSelectedAddress(selectedAddress)
    }

    override fun getSelectedAddress(): Int {
        return settingsStorageInterface.getSelectedAddress()
    }

    override fun firstStartComplete() {
        settingsStorageInterface.firstStartComplete()
    }

    override fun isFirstStart(): Boolean {
        return settingsStorageInterface.isFirstStart()
    }
}