package com.example.smartcontrollerv3.main.presentation.addAddress

import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.address.Address
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class AddAddressViewModel(
    private val navigationController: NavigationController,
    private val addNewAddressUseCase: AddNewAddressUseCase
) : ViewModel() {

    fun saveAddress(address: Address) {

        val saveResult = addNewAddressUseCase.execute(address = address)

        if(saveResult) {
            navigateBack()
        }
    }

    fun navigateBack() {
        navigationController.navigateBack()
    }

}