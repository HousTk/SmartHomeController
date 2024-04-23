package com.example.data.data.repository

import android.content.Context
import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.models.address.Address
import com.example.domain.domain.repository.AddressRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFS_ADDRESS_NAME = "addresses"
private const val SHARED_PREFS_KEY_ADDRESS = "address"

class AddressRepositoryImplementation(
    context:Context,
    private val settingsStorageInterface: SettingsStorageInterface
    ) : AddressRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_ADDRESS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()
    private val gson = Gson()


    override fun addAddress(address: Address): Boolean {

        val listTMP = getAddressList()

        listTMP.add(address)

        return saveAddressList(listTMP)
    }

    override fun removeAddress(addressPosition: Int): Boolean {

        val listTMP = getAddressList()

        try {

            listTMP.removeAt(addressPosition)

        } catch (e:Exception){

            return false

        }

        return saveAddressList(listTMP)

    }

    override fun getAddress(addressPosition: Int): Address {

        return getAddressList()[addressPosition]

    }

    override fun getCurrentAddress(): Address {
        val position = settingsStorageInterface.getSelectedAddress()

        return getAddress(position)
    }

    override fun getAddressList(): ArrayList<Address> {

        val jsonAddressList = sharedPreferences.getString(SHARED_PREFS_KEY_ADDRESS, null)

        val typeIds: Type = object: TypeToken<ArrayList<Address>>(){}.type

        val addressList = gson.fromJson<Any>(jsonAddressList, typeIds) as ArrayList<Address>?

        return addressList ?: ArrayList()

    }


    private fun saveAddressList(addressList: ArrayList<Address>):Boolean{

        val jsonAddressList:String = gson.toJson(addressList)

        spEditor.putString(SHARED_PREFS_KEY_ADDRESS,jsonAddressList).apply()

        return true
    }

}