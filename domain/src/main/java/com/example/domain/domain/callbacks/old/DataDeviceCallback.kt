package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.Device

interface DataDeviceCallback {

    fun onComplete(data : Device)

    fun onFail(message : String)
}