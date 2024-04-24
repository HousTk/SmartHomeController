package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.Room

interface DataLongCallback {

    fun onComplete(data : Long)

    fun onFail(message : String)

}