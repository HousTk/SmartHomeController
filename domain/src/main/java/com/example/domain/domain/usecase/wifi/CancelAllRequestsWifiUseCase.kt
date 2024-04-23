package com.example.domain.domain.usecase.wifi

class CancelAllRequestsWifiUseCase (
    private val wifiJobList: WifiJobList
){

    fun execute(){
        wifiJobList.clear()
    }

}