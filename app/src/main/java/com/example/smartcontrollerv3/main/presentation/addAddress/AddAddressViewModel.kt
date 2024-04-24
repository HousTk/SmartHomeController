package com.example.smartcontrollerv3.main.presentation.addAddress

import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAddressViewModel(
    private val navigationController: NavigationController,
    private val addNewAddressUseCase: AddNewAddressUseCase
) : ViewModel() {

    fun saveAddress(address: SaveParamsAddress) {

        CoroutineScope(Dispatchers.IO).launch{

            if(addNewAddressUseCase.execute(address)){

                withContext(Dispatchers.Main){

                    navigateBack()

                }

            }



        }


    }

    fun navigateBack() {
        navigationController.navigateBack()
    }

}