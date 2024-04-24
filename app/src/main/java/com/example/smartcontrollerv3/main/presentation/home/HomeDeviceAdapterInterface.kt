package com.example.smartcontrollerv3.main.presentation.home

import com.example.domain.domain.models.main.Device

interface HomeDeviceAdapterInterface {
    fun onDeviceClick(device: Device)
    fun deleteDevice(deviceId: Long)
    fun turnOnDevice(deviceId: Long)
    fun turnOffDevice(deviceId: Long)
}