package com.example.domain.domain.models.main


data class Room(
    var id:Long,
    var name:String,
    var icon:Int,
    var devicesIdsInRoom: List<Long>?
)
