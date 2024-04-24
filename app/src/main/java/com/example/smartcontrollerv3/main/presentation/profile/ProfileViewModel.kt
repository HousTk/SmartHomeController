package com.example.smartcontrollerv3.main.presentation.profile

import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import com.example.domain.domain.usecase.user.LogOutUseCase
import com.example.smartcontrollerv3.R

class ProfileViewModel(
private val logOutUseCase: LogOutUseCase
):ViewModel() {

    fun onClickMore(view: View){

        val menu = PopupMenu(view.context, view)

        menu.inflate(R.menu.profile)

        menu.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuProfileLogOut -> {
                    logOut()
                }

            }

            return@setOnMenuItemClickListener true
        }


        menu.show()

    }

    private fun logOut(){

        logOutUseCase.execute()

    }
}