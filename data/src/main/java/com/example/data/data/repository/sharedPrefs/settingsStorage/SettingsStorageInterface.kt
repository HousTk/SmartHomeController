package com.example.data.data.repository.sharedPrefs.settingsStorage

interface SettingsStorageInterface {

    fun changeSelectedAddress(selectedAddressKey : String):Boolean
    fun getSelectedAddressKey():String

    fun savePosts(posts:HashMap<String, String>)
    fun getPosts():HashMap<String, String>
}