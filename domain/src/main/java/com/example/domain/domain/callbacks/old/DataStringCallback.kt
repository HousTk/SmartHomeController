package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.Room

interface DataStringCallback {

    fun onComplete(data : String)

    fun onFail(message : String)

}