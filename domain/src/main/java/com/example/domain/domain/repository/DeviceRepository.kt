package com.example.domain.domain.repository

import com.example.domain.domain.models.device.Device
import com.example.domain.domain.models.device.SavingDeviceParam

interface DeviceRepository {

    fun addDevice(savingParams: SavingDeviceParam): Boolean

    fun getDevice(id:Int): Device

    fun deleteDevice(id: Int):Boolean

    fun updateSettings(settings:Any, id: Int):Boolean

    fun updateDeviceName(name:String, id: Int):Boolean

    fun updateDeviceIp(ip:String, id: Int):Boolean

    fun updateDeviceType(type:Int, id: Int):Boolean

    fun updateDeviceStatus(status:Boolean, id: Int):Boolean

    fun updateDevice(device: Device, id: Int):Boolean

    fun updateDeviceState(state:Boolean, id:Int):Boolean

    fun setUpdatingStatus(isUpdating:Boolean, id: Int):Boolean
}