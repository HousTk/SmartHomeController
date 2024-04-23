package com.example.domain.domain.usecase.wifi

import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetDeviceTypeWifiUseCase(
    private val wifiHandler: WifiHandler,
    private val deviceRepository: DeviceRepository,
    private val wifiJobList: WifiJobList
) {

    fun execute(callback: WifiCallback, deviceId: Int) {

        deviceRepository.setUpdatingStatus(isUpdating = true, deviceId)

        callback.updateDevice()

        val device = deviceRepository.getDevice(deviceId)

        val job = CoroutineScope(Dispatchers.IO).launch {

            val type = wifiHandler.getType(ip = device.ip)

            withContext(Dispatchers.Main) {
                if (type != null) {

                    deviceRepository.updateDeviceType(type = type, id = deviceId)
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

        wifiJobList.addToList(job = job)

    }
}