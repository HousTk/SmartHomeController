package com.example.domain.domain.callbacks.old

interface DataListCallback {

    fun onComplete(data : List<*>)

    fun onFail(message : String)

}