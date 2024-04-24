package com.example.smartcontrollerv3.main.presentation.logIn.createFirstAddress

import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromCFAUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateFirstAddressViewModel(
    private val addNewAddressUseCase: AddNewAddressUseCase,
    private val navigateFromCFAUseCase: NavigateFromCFAUseCase

):ViewModel() {

    fun onSave(name:String, ssid:String){

        if(name != "" && ssid != ""){

            val address = SaveParamsAddress(name = name, wifiSSID = ssid)


            CoroutineScope(Dispatchers.IO).launch{

                if(addNewAddressUseCase.execute(address = address)){

                    withContext(Dispatchers.Main){

                        onSucceed()

                    }

                }else{

                    withContext(Dispatchers.Main){

                        onFail()

                    }

                }

            }

        }
    }

    private fun onSucceed(){

        navigateFromCFAUseCase.execute()

    }

    private fun onFail(){

    }



}