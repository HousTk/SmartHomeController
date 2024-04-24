package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.Room

interface DataRoomCallback {

    fun onComplete(data : Room)

    fun onFail(message : String)

}