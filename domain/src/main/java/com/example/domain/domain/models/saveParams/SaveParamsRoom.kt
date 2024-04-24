package com.example.domain.domain.models.saveParams

data class SaveParamsRoom(
    val name:String,
    val icon:Int,
    val devicesIdsInRoom: ArrayList<Long>?
)
