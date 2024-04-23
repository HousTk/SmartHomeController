package com.example.domain.domain.usecase.wifi

import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendDeviceSettingsWifiUseCase(
    private val wifiHandler: WifiHandler,
    private val deviceRepository: DeviceRepository,
    private val wifiJobList: WifiJobList
) {

    fun execute(callback: WifiCallback, deviceId: Int, settings: Any) {

        deviceRepository.setUpdatingStatus(isUpdating = true, deviceId)

        callback.updateDevice()

        val device = deviceRepository.getDevice(deviceId)

        val type = device.type

        val job = CoroutineScope(Dispatchers.IO).launch() {

            val settingsResult = wifiHandler.sendSettings(deviceType = type, ip = device.ip, settings = settings)

            withContext(Dispatchers.Main) {
                if (settingsResult != null) {
                    deviceRepository.updateSettings(settingsResult, deviceId)
                    deviceRepository.updateDeviceStatus(status = true, id = deviceId)
                    callback.requestComplete(true)

                } else {
                    deviceRepository.updateDeviceStatus(status = false, id = deviceId)
                    callback.requestComplete(false)
                }

                deviceRepository.setUpdatingStatus(isUpdating = false, deviceId)
                callback.updateDevice()
            }
        }

        wifiJobList.addToList(job)

    }

}