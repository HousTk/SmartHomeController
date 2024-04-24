package com.example.domain.domain.usecase

import com.example.domain.domain.models.main.Device
import com.example.domain.domain.repository.AddressRepository


class GetDevicesListInRoomUseCase(
    private val addressRepository: AddressRepository
) {

    suspend fun execute(roomId:Long):List<Device>{

        val listTmp = ArrayList<Device>()

        val ids = addressRepository.getRoom(addressKey = null, roomId = roomId).devicesIdsInRoom ?: return emptyList()

        repeat(ids.size){

            listTmp.add( addressRepository.getDevice(addressKey = null, ids[it]) )

        }

        return listTmp
    }

}