package com.example.domain.domain.models.main

import com.example.domain.domain.models.device.settings.Settings

data class Device(
    var id:Long,
    var name:String,
    var type:Int = -1,
    var ip:String,
    var status:Boolean = false,
    //var state:Boolean = false,
    var isUpdating:Boolean = false,
    var settings: Settings
)