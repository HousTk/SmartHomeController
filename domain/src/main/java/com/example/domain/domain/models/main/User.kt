package com.example.domain.domain.models.main

data class User(
    val uid:String,
    val name:String,
    val email:String,
    var addressesKeys:List<String>
)
