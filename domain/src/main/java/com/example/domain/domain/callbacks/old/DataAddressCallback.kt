package com.example.domain.domain.callbacks.old

import com.example.domain.domain.models.main.Address

interface DataAddressCallback {

    fun onComplete(data : Address)

    fun onFail(message : String)
}