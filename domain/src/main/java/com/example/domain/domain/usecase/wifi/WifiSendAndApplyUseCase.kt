package com.example.domain.domain.usecase.wifi

import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.repository.AddressRepository

class WifiSendAndApplyUseCase(
    private val wifiHandler: WifiHandler,
    private val addressRepository: AddressRepository
) {

    suspend fun settings(callback: WifiCallback, device: Device, settings: Settings) {
        var result = false

        addressRepository.setUpdatingStatus(isUpdating = true, id = device.id)

        val settingsResult =
            wifiHandler.sendSettings(ip = device.ip, settings = settings)


        if (settingsResult != null) {

            device.settings = settingsResult
            device.status = true

            result = true

        } else {

            device.status = false

            result = false
        }

        device.isUpdating = false

        addressRepository.addDevice(addressKey = null, device = device)

        callback.requestComplete(result)

    }

    suspend fun turnOff(callback: WifiCallback, device: Device) {
        var result = false

        addressRepository.setUpdatingStatus(isUpdating = true, id = device.id)

        val state = wifiHandler.sendTurnOff(ip = device.ip)

        if (state != null) {

            device.settings.state = state
            device.status = true

            result = true

        } else {
            device.status = false

            result = false
        }

        device.isUpdating = false

        addressRepository.addDevice(addressKey = null, device = device)

        callback.requestComplete(result)
    }

    suspend fun turnOn(callback: WifiCallback, device: Device) {
        var result = false

        addressRepository.setUpdatingStatus(isUpdating = true, id = device.id)

        val state = wifiHandler.sendTurnOn(ip = device.ip)

        if (state != null) {

            device.settings.state = state
            device.status = true

            result = true

        } else {
            device.status = false

            result = false
        }

        device.isUpdating = false

        addressRepository.addDevice(addressKey = null, device = device)

        callback.requestComplete(result)

    }
}