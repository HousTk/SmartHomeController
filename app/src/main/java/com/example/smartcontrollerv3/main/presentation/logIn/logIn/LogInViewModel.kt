package com.example.smartcontrollerv3.main.presentation.logIn.logIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.navigation.NavigateFromLogIn
import com.example.domain.domain.usecase.user.SignInUseCase
import com.example.smartcontrollerv3.main.utils.makeLogE
import com.example.smartcontrollerv3.main.utils.makeLogI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel(
    private val navigateFromLogIn: NavigateFromLogIn,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    val errorMessage = MutableLiveData<String?>()

    fun logIn(email: String, password: String){

        if(email.isEmpty()) {
            errorMessage.value = "Введите email"
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.value = "Неправильно введен email"
            return
        }
        if(password.isEmpty()) {
            errorMessage.value = "Введите пароль"
            return
        }

        errorMessage.value = null

        CoroutineScope(Dispatchers.IO).launch{

            try {

                if(signInUseCase.execute(email, password)){

                    withContext(Dispatchers.Main) {
                        makeLogI("Log in successful!")
                        signInSucc()
                    }
                }

            }catch (e: Exception){

                withContext(Dispatchers.Main) {
                    makeLogE("Log in error")
                    errorMessage.value = "Проверьте правильность введенных данных"
                }

            }



            }


    }

    private fun signInSucc(){
        navigateFromLogIn.execute()
    }

}