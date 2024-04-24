package com.example.smartcontrollerv3.main.presentation.logIn.logUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.navigation.NavigateFromLogUpUseCase
import com.example.domain.domain.usecase.user.RegisterNewUserUseCase
import com.example.smartcontrollerv3.main.utils.makeLogE
import com.example.smartcontrollerv3.main.utils.makeLogI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogUpViewModel(
    private val navigateFromLogUpUseCase: NavigateFromLogUpUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    val errorMessage = MutableLiveData<String?>()

    fun logUp(email: String, password: String) {

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
        if(password.length < 6 ) {
            errorMessage.value = "Минимальный размер пароля 6 символов"
            return
        }

        errorMessage.value = null

        CoroutineScope(Dispatchers.IO).launch {

            try {

                if (registerNewUserUseCase.execute(email, password)) {

                    withContext(Dispatchers.Main) {
                        makeLogI("Registration success")

                        logUpSuccess()
                    }

                }


            } catch (e: Exception) {
                makeLogE("Registration error - $e")
                withContext(Dispatchers.Main) {
                    errorMessage.value = (
                            when ("$e") {

                                "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account." -> {
                                    "Данный email уже используется"
                                }

                                else -> {
                                    "Что-то пошло не так"
                                }
                            }
                            )
                }

            }


        }


    }

    private fun logUpSuccess() {

        navigateFromLogUpUseCase.toHome()

    }

    private fun logUpFail(failure: String) {

    }

}