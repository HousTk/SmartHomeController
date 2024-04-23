package com.example.data.data.repository

import android.content.Context
import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.models.device.SavingDeviceParam
import com.example.domain.domain.models.device.settings.LedControllerSetts
import com.example.domain.domain.models.device.settings.LightControllerSetts
import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type

private const val SHARED_PREFS_NAME = "devices"
private const val SHARED_PREFS_KEY_DEVICE = "device"

class DeviceRepositoryImplementation(
    context : Context,
    private val settingsStorageInterface: SettingsStorageInterface

) : DeviceRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()

    private fun getKey():String{
        return "$SHARED_PREFS_KEY_DEVICE${settingsStorageInterface.getSelectedAddress()}"
    }

    override fun addDevice(savingParams: SavingDeviceParam): Boolean {

        val device = Device(name = savingParams.name, ip = savingParams.ip, id = savingParams.id)
        val id = savingParams.id

        val gson = Gson()

        val jsonDevice:String = gson.toJson(device)

        spEditor.putString("${getKey()}$id", jsonDevice).apply()

        return true

    }

    override fun getDevice(id:Int): Device {

        val gson = Gson()

        val jsonDevice = sharedPreferences.getString("${getKey()}$id", null)

        val typeDevice: Type = object: TypeToken<Device>(){}.type

        val device = gson.fromJson<Any>(jsonDevice, typeDevice) as Device?

        return device ?: Device("error", -1, "0", -1)

    }

    override fun deleteDevice(id: Int): Boolean {

        try {
            sharedPreferences.edit().remove("${getKey()}$id").apply()

            return true
        } catch (e: Exception){

            return false
        }

    }

    override fun updateSettings(settings: Any, id: Int): Boolean {

        val device = getDevice(id)

        when(settings){
            is LedControllerSetts ->{

                if(device.type == TYPE_LEDCONTROLLER){
                    device.settings = settings
                } else{
                    return false
                }

            }

            is LightControllerSetts ->{

                if(device.type == TYPE_LIGHTCONTROLLER){
                    device.settings = settings
                } else{
                    return false
                }

            }

        }

        return saveDevice(device = device, id = id)
    }

    override fun updateDeviceName(name: String, id: Int): Boolean {
        val device = getDevice(id)

        device.name = name

        return saveDevice(device = device, id = id)
    }

    override fun updateDeviceIp(ip: String, id: Int): Boolean {
        val device = getDevice(id)

        device.ip = ip

        return saveDevice(device = device, id = id)
    }

    override fun updateDeviceType(type: Int, id: Int): Boolean {
        val device = getDevice(id)

        device.type = type

        return saveDevice(device = device, id = id)
    }

    override fun updateDeviceStatus(status: Boolean, id: Int): Boolean {
        val device = getDevice(id)

        device.status = status

        return saveDevice(device = device, id = id)
    }

    override fun updateDevice(device: Device, id: Int): Boolean {

        return saveDevice(device = device, id = id)

    }

    override fun updateDeviceState(state: Boolean, id: Int): Boolean {
        val device = getDevice(id)

        device.state = state

        return saveDevice(device = device, id = id)
    }

    override fun setUpdatingStatus(isUpdating: Boolean, id: Int): Boolean {
        val device = getDevice(id)

        device.isUpdating = isUpdating

        return saveDevice(device = device, id = id)
    }

    private fun saveDevice(device: Device, id:Int):Boolean{

        val gson = Gson()

        val jsonDevice:String = gson.toJson(device)

        spEditor.putString("${getKey()}$id", jsonDevice).apply()

        return true
    }
}