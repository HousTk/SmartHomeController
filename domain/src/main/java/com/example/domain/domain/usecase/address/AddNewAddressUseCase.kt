package com.example.domain.domain.usecase.address

import com.example.domain.domain.models.address.Address
import com.example.domain.domain.models.room.Room
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.RoomRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.utils.ALLDEVICES_ROOM_NAME

class AddNewAddressUseCase(
    private val addressRepository: AddressRepository,
    private val roomRepository: RoomRepository,
    private val settingsRepository: SettingsRepository,
    private val allDevicesRoomIcon: Int
) {

    fun execute(address:Address):Boolean{

        addressRepository.addAddress(address = address)

        val selectedAddress = settingsRepository.getSelectedAddress()

        val addressesList = addressRepository.getAddressList()

        settingsRepository.changeSelectedAddress(addressesList.size - 1)

        val room = Room(ALLDEVICES_ROOM_NAME, icon = allDevicesRoomIcon, ArrayList())
        roomRepository.addToList(room)

        settingsRepository.changeSelectedAddress(selectedAddress)

        return true
    }

}