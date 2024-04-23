package com.example.data.data.repository.settingsStorage

import android.content.Context
import com.google.gson.Gson

private const val SHARED_PREFS_NAME = "Settings"
private const val SHARED_PREFS_KEY_SELECTED_ADDRESS = "Address"
private const val SHARED_PREFS_KEY_FIRST_START = "FirstStart"

class SettingsStorageImpl(context: Context) : SettingsStorageInterface {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()

    override fun changeSelectedAddress(selectedAddress: Int): Boolean {

        spEditor.putInt(SHARED_PREFS_KEY_SELECTED_ADDRESS, selectedAddress).apply()

        return true

    }

    override fun getSelectedAddress(): Int {

        return sharedPreferences.getInt(SHARED_PREFS_KEY_SELECTED_ADDRESS,0)

    }

    override fun firstStartComplete() {

        spEditor.putBoolean(SHARED_PREFS_KEY_FIRST_START, false).apply()

    }

    override fun isFirstStart(): Boolean {

        return sharedPreferences.getBoolean(SHARED_PREFS_KEY_FIRST_START, true)

    }

}