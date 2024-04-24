package com.example.domain.domain.usecase.wifi

import com.example.domain.domain.models.main.Device
import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.repository.AddressRepository


class WifiGetAndApplyUseCase(
    private val wifiHandler: WifiHandler,
    private val addressRepository: AddressRepository
) {

    suspend fun settings(callback: WifiCallback, device: Device) {
        var result = false

        addressRepository.setUpdatingStatus(isUpdating = true, id = device.id)

        val settings = wifiHandler.getSettings(ip = device.ip)

        if (settings != null) {

            device.settings = settings
            device.status = true

            result = true

        } else {

            device.status = false
            device.settings.state = false

            result = false
        }

        device.isUpdating = false

        addressRepository.addDevice(addressKey = null, device = device)

        callback.requestComplete(result)
    }

//    suspend fun state(callback: WifiCallback, deviceId: Long) {
//
//
//        databaseRepository.setUpdatingStatus(isUpdating = true, id = deviceId)
//
//        callback.updateDevice()
//
//        val device = databaseRepository.getDevice(deviceId)
//
//        val state = wifiHandler.getState(ip = device.ip)
//
//
//        if (state != null) {
//            databaseRepository.updateDeviceState(state = state, id = deviceId)
//            databaseRepository.updateDeviceStatus(status = true, id = deviceId)
//
//            callback.requestComplete(true)
//
//        } else {
//            databaseRepository.updateDeviceStatus(status = false, id = deviceId)
//            callback.requestComplete(false)
//        }
//
//        databaseRepository.setUpdatingStatus(isUpdating = false, id = deviceId)
//        callback.updateDevice()
//
//
//    }

    suspend fun type(callback: WifiCallback, device: Device) {

        var result = false

        addressRepository.setUpdatingStatus(isUpdating = true, id = device.id)

        val type = wifiHandler.getType(ip = device.ip)

        if (type != null) {

            device.type = type
            device.status = true

            result = true

        } else {

            device.status = false
            device.settings.state = false

            result = false
        }

        device.isUpdating = false

        addressRepository.addDevice(addressKey = null, device = device)

        callback.requestComplete(result)
    }

}