package com.example.domain.domain.models.device.settings

data class Settings(
    var state:Boolean = false,

    var brightness:Int? = null,
    var red:Int? = null,
    var green:Int? = null,
    var blue:Int? = null,

    var normalState:Boolean? = null,

    var targetTemp:Int? = null,

    var movementSensorTimeout:Int? = null
)