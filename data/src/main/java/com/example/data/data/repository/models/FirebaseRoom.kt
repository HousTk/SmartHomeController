package com.example.data.data.repository.models

data class FirebaseRoom(
    val id:Long,
    val name:String,
    val icon:Int,
    val devicesIdsInRoom: ArrayList<Long>
)