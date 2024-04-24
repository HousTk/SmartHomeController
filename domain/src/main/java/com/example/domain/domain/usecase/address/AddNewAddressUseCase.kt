package com.example.domain.domain.usecase.address


import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.domain.domain.utils.ALLDEVICES_ROOM_NAME

class AddNewAddressUseCase(
    private val addressRepository: AddressRepository,
    private val authRepository: AuthRepository
) {

    suspend fun execute(address: SaveParamsAddress, usersUids:List<Long>? = null):Boolean {

        val currentUser = authRepository.getCurrentUser()

        return addressRepository.saveAddress(
            address = address,
            listOf(currentUser.uid)
        ) != null

    }

}