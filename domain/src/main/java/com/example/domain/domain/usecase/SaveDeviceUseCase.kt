package com.example.domain.domain.usecase

import com.example.domain.domain.models.device.SavingDeviceParam
import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.repository.IdRepository
import com.example.domain.domain.repository.RoomRepository

class SaveDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    private val idRepository: IdRepository,
    private val roomRepository: RoomRepository
) {

    fun execute(savingDeviceParam: SavingDeviceParam): Boolean {

        if (
            savingDeviceParam.name != "" &&
            savingDeviceParam.ip != ""
        ) {

            val result = deviceRepository.addDevice(savingDeviceParam)
            val result2 = idRepository.addToList(savingDeviceParam.id)
            val result3 = roomRepository.addDeviceIdToAllDevicesRoom(savingDeviceParam.id)

                if (result && result2) {

                    return true

                } else {

                    return false

                }
        } else {
            return false
        }


    }
}