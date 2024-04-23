package com.example.data.data.repository.settingsStorage

interface SettingsStorageInterface {

    fun changeSelectedAddress(selectedAddress:Int):Boolean

    fun getSelectedAddress():Int

    fun firstStartComplete()

    fun isFirstStart():Boolean
}