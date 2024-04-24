package com.example.data.data.repository

import com.example.data.data.repository.sharedPrefs.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.repository.SettingsRepository

class SettingsRepositoryImplementation(

    private val settingsStorageInterface: SettingsStorageInterface

):SettingsRepository {
    override fun getAndApplyPosts() {

        val posts = settingsStorageInterface.getPosts()

        settingsStorageInterface.savePosts(posts)
    }

    override fun getPosts(): HashMap<String, String> {

        return settingsStorageInterface.getPosts()

    }

    override fun getSelectedAddressKey(): String {
        return settingsStorageInterface.getSelectedAddressKey()
    }

    override fun changeSelectedAddressKey(selectedAddressKey: String) {
        settingsStorageInterface.changeSelectedAddress(selectedAddressKey)
    }


}