package com.example.domain.domain.repository



interface SettingsRepository {

    fun getAndApplyPosts()
    fun getPosts():HashMap<String, String>

    fun getSelectedAddressKey():String

    fun changeSelectedAddressKey(selectedAddressKey: String)
}