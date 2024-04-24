package com.example.data.data.repository.models

data class FirebaseUser(
    val uid:String,
    val name:String,
    val email:String,
    var addressesKeys:List<String>
)