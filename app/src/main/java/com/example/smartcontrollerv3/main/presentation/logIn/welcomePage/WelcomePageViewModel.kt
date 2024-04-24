package com.example.smartcontrollerv3.main.presentation.logIn.welcomePage

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.navigation.NavigateFromWelcomePageUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class WelcomePageViewModel(
    private val navigateFromWelcomePageUseCase: NavigateFromWelcomePageUseCase
):ViewModel() {

    fun signIn(){
        navigateFromWelcomePageUseCase.toLogIn()
    }

    fun signUp(){
        navigateFromWelcomePageUseCase.toLogUp()
    }

}