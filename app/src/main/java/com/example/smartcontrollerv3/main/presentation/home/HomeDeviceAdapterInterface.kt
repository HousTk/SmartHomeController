package com.example.smartcontrollerv3.main.presentation.home

interface HomeDeviceAdapterInterface {
    fun onDeviceClick(deviceId:Int)
    fun deleteDevice(deviceId: Int)
    fun turnOnDevice(deviceId: Int)
    fun turnOffDevice(deviceId: Int)
}