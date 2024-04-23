package com.example.domain.domain.repository

interface IdRepository {

    fun addToList(id:Int):Boolean

    fun getIdsList():ArrayList<Int>

    fun removeFromList(id:Int):Boolean
}