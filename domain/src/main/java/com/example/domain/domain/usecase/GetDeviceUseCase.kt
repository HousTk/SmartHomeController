package com.example.domain.domain.usecase

import com.example.domain.domain.models.device.Device
import com.example.domain.domain.repository.DeviceRepository

class GetDeviceUseCase(private val deviceRepository: DeviceRepository) {
    fun execute(id:Int): Device {

        return deviceRepository.getDevice(id)
    }
}