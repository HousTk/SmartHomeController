package com.example.domain.domain.models.device

data class Device(
    var name:String,
    var type:Int = -1,
    var ip:String,
    var id:Int,
    var status:Boolean = false,
    var state:Boolean = false,
    var isUpdating:Boolean = false,
    var settings: Any? = null
)