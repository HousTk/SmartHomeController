package com.example.smartcontrollerv3.main.presentation.logIn

import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.address.Address
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.domain.domain.usecase.settings.FirstStartCompleteUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class CreateFirstAddressViewModel(
    private val firstStartCompleteUseCase: FirstStartCompleteUseCase,
    private val addNewAddressUseCase: AddNewAddressUseCase,
    private val navigationController: NavigationController

):ViewModel() {

    fun onSave(name:String, ssid:String):Boolean{
        if(name != "" && ssid != ""){

            val address = Address(name = name, wifiSSID = ssid)

            val saveResult = addNewAddressUseCase.execute(address = address)

            if(saveResult){

                firstStartCompleteUseCase.execute()
                navigationController.navigateTo(R.id.homeFragment)
                return true

            }else{
                return false
            }

        }else{
            return false
        }
    }



}