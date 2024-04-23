package com.example.domain.domain.usecase

import com.example.domain.domain.models.device.Device
import com.example.domain.domain.repository.DeviceRepository

class GetDeviceListUseCase(private val deviceRepository: DeviceRepository) {

    fun execute(idsList: ArrayList<Int>):ArrayList<Device>{
        val deviceListTmp = ArrayList<Device>()

        repeat(idsList.size){
            deviceListTmp.add( deviceRepository.getDevice(idsList[it]) )
        }

        return deviceListTmp
    }

}