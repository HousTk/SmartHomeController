package com.example.firebase.storage.rooms

import com.example.data.data.repository.models.FirebaseRoom


interface FirebaseDatabaseRoomsStorageInterface {



    suspend fun getRoomList(addressKey:String):Any?

    suspend fun getRoom(roomId:Long, addressKey:String):Any?

    suspend fun saveRoom(room: FirebaseRoom, addressKey:String):Boolean

    suspend fun deleteRoom(roomId: Long, addressKey: String):Boolean



}