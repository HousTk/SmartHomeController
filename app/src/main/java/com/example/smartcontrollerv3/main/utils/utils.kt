package com.example.smartcontrollerv3.main.utils

import android.util.Log
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERHEATER
import com.example.smartcontrollerv3.R

const val SUCCESS = "success"
const val ERROR = "err"

// массив иконок для комнат
val ROOM_ICONS_ARRAY = arrayOf(
    R.drawable.ic_room_bedroom_white,
    R.drawable.ic_room_bedroom2_white,
    R.drawable.ic_room_bathroom_white,
    R.drawable.ic_room_bedroombaby_white,
    R.drawable.ic_room_checkroom_white,
    R.drawable.ic_room_kitchen_white,
    R.drawable.ic_room_living_white,
    R.drawable.ic_room_other_white
)

// возвращает инокну девайса drawable
fun getDeviceIconByType(deviceType: Int): Int {
    return when (deviceType) {
        TYPE_LEDCONTROLLER -> R.drawable.ic_led_white
        TYPE_LIGHTCONTROLLER -> R.drawable.ic_light_white
        TYPE_MOVE_LIGHTCONTROLLER -> R.drawable.ic_light_white
        TYPE_SWITCH_LIGHTCONTROLLER -> R.drawable.ic_light_white
        TYPE_SWITCH_MOVE_LIGHTCONTROLLER -> R.drawable.ic_light_white
        TYPE_WATERCONTROLLER -> R.drawable.ic_water_pump_white
        TYPE_WATERHEATER -> R.drawable.ic_water_heat_white
        else -> R.drawable.ic_type_other_white
    }
}

// возвращает имя девайса
fun getDeviceNameByType(deviceType: Int): String {
    return when (deviceType) {
        TYPE_LIGHTCONTROLLER -> "LightController"
        TYPE_MOVE_LIGHTCONTROLLER -> "MoveLightController"
        TYPE_SWITCH_LIGHTCONTROLLER -> "SwitchLightController"
        TYPE_SWITCH_MOVE_LIGHTCONTROLLER -> "SwitchMoveLightController"
        TYPE_LEDCONTROLLER -> "LedController"
        TYPE_WATERCONTROLLER -> "WaterController"
        TYPE_WATERHEATER -> "WaterHeater"


        -1 -> "Other"

        else -> "Unknown"
    }
}

fun getDisplayedType(deviceType: Int): Int {
    return when (deviceType) {
        TYPE_LEDCONTROLLER -> R.string.led
        TYPE_LIGHTCONTROLLER, TYPE_MOVE_LIGHTCONTROLLER, TYPE_SWITCH_LIGHTCONTROLLER, TYPE_SWITCH_MOVE_LIGHTCONTROLLER -> R.string.light
        TYPE_WATERCONTROLLER -> R.string.waterController
        else -> R.string.other
    }
}


// логи
fun makeLogI(logText: String) {
    Log.i("MyLog", logText)
}

fun makeLogE(logText: String) {
    Log.e("MyLog", logText)
}

fun makeLogW(logText: String) {
    Log.w("MyLog", logText)
}