package com.example.data.data.repository.models

data class FirebaseDevice(
    val name:String,
    val ip:String,
    val id:Long,
    val type:Int,
    val settings: HashMap<String,*>
)
