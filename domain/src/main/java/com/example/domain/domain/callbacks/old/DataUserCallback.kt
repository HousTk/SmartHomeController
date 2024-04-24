package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.User

interface DataUserCallback {

    fun onComplete(user : User)

    fun onFail(message : String)

}