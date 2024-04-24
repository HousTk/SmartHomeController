package com.example.domain.domain.usecase

import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.repository.AddressRepository

class SaveDeviceUseCase(
    private val addressRepository: AddressRepository

) {

    suspend fun execute(saveParamsDevice: SaveParamsDevice):Long {

        return addressRepository.addDevice(addressKey = null, savingParams = saveParamsDevice)

    }
}