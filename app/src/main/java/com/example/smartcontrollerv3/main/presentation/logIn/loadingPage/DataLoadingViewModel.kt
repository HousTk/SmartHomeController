package com.example.smartcontrollerv3.main.presentation.logIn.loadingPage

import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.data.ActualizeAddresses
import com.example.domain.domain.usecase.navigation.NavigateFromDataLoadingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataLoadingViewModel(
    private val navigateFromDataLoadingUseCase: NavigateFromDataLoadingUseCase,
    private val actualizeAddresses: ActualizeAddresses
) : ViewModel() {

    fun init() {

        CoroutineScope(Dispatchers.Main).launch{

            if(actualizeAddresses.isEmptyAddresses()){

                withContext(Dispatchers.Main){

                    navigateFromDataLoadingUseCase.toCFA()

                }

            }else{

                withContext(Dispatchers.Main){

                    actualizeAddresses.execute()

                    navigateFromDataLoadingUseCase.toHome()

                }

            }

        }

    }

}