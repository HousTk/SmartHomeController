package com.example.data.data.repository.sharedPrefs.settingsStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFS_NAME = "Settings"
private const val SHARED_PREFS_KEY_SELECTED_ADDRESS = "Address"
private const val SHARED_PREFS_KEY_POSTS = "Posts"

class SettingsStorageImpl(context: Context) : SettingsStorageInterface {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()

    override fun changeSelectedAddress(selectedAddressKey: String): Boolean {

        spEditor.putString(SHARED_PREFS_KEY_SELECTED_ADDRESS, selectedAddressKey).apply()

        return true

    }

    override fun getSelectedAddressKey(): String {

        return sharedPreferences.getString(SHARED_PREFS_KEY_SELECTED_ADDRESS,"") ?: ""

    }

    override fun savePosts(posts: HashMap<String, String>) {

        val gson = Gson()

        val jsonPosts: String = gson.toJson(posts)

        spEditor.putString(SHARED_PREFS_KEY_POSTS, jsonPosts).apply()

    }

    override fun getPosts(): HashMap<String, String> {

        val gson = Gson()

        val jsonPosts = sharedPreferences.getString(SHARED_PREFS_KEY_POSTS, null)

        val typePosts: Type = object : TypeToken<HashMap<*, *>>() {}.type

        val posts = gson.fromJson<Any>(jsonPosts, typePosts) as HashMap<String, String>?



        return posts ?: HashMap()

    }

}