package com.example.domain.domain.usecase.wifi

interface WifiCallback {
    fun requestComplete(isSuccess: Boolean)
    fun updateDevice()
}