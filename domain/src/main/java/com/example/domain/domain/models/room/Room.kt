package com.example.domain.domain.models.room

import com.example.domain.domain.utils.POWERBUTTON_ACTION_NONE

data class Room(
    var name:String,
    var icon:Int,
    var devicesIdsInRoom: ArrayList<Int>
)
