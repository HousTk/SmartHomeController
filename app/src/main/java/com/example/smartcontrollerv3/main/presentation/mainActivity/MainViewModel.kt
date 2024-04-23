package com.example.smartcontrollerv3.main.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.usecase.address.GetAddressesListUseCase
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class MainViewModel(
    private val navigationController : NavigationController
):ViewModel() {


    fun setupNavController(controller: NavController){

        navigationController.setUpNavHost(controller)

    }

}