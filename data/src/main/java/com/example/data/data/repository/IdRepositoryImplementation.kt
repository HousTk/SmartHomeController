package com.example.data.data.repository

import android.content.Context
import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.repository.IdRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFS_NAME = "ids"
private const val SHARED_PREFS_KEY_IDSLIST = "idsList"

class IdRepositoryImplementation(
    context : Context,
    private val settingsStorageInterface: SettingsStorageInterface

):IdRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()
    private val gson = Gson()


    override fun addToList(id: Int): Boolean {

        val idsList = getIdsList()
        idsList.add(id)

        saveIdsList(idsList)

        return true
    }

    override fun getIdsList(): ArrayList<Int> {

        val jsonIdsList = sharedPreferences.getString(getKey(), null)

        val typeIds: Type = object: TypeToken<ArrayList<Int>>(){}.type

        val idsList = gson.fromJson<Any>(jsonIdsList, typeIds) as ArrayList<Int>?

        return idsList ?: ArrayList()

    }

    override fun removeFromList(id: Int): Boolean {

        try {
            removeId(id)

            return true

        }catch (e:Exception){
            return false
        }

    }


    private fun saveIdsList(idsList:ArrayList<Int>):Boolean{

        val jsonIdsList:String = gson.toJson(idsList)

        spEditor.putString(getKey(),jsonIdsList).apply()

        return true
    }

    private fun removeId(id:Int){
        val idsList = getIdsList()
        idsList.remove(id)

        saveIdsList(idsList)
    }

    private fun getKey():String{
        return "$SHARED_PREFS_KEY_IDSLIST${settingsStorageInterface.getSelectedAddress()}"
    }
}