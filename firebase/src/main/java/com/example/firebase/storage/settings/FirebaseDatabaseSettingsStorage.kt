package com.example.firebase.storage.settings

import com.example.firebase.utils.DATABASE_URL
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

private const val SETTINGS_KEY = "App settings"

private const val POSTS_KEY = "Posts"

class FirebaseDatabaseSettingsStorage(): FirebaseDatabaseSettingsStorageInterface {

    private val database =
        Firebase.database(DATABASE_URL)
            .reference
            .child(SETTINGS_KEY)
            .child(POSTS_KEY)


    override suspend fun get(): Any? {

       return  database.get().await().value

    }

}