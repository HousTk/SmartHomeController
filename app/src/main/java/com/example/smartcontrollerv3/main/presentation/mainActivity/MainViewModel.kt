package com.example.smartcontrollerv3.main.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.domain.domain.usecase.user.IsSignedInUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val navigationController : NavigationController,
    private val isSignedInUseCase: IsSignedInUseCase
):ViewModel() {


    fun init(){



    }

    fun setupNavController(controller: NavController){

        val isSignedIn = isSignedInUseCase.execute()


        navigationController.setUpNavHost(controller)

        navigationController.setStartDestination(
            if(isSignedIn){
                R.id.dataLoadingFragment
            }else{
                R.id.welcomePageFragment
            }
        )


    }

}