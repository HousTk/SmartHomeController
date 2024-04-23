package com.example.domain.domain.repository

interface SettingsRepository {

    fun changeSelectedAddress(selectedAddress:Int):Boolean

    fun getSelectedAddress():Int

    fun firstStartComplete()

    fun isFirstStart():Boolean

}