package com.example.data.data.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.data.repository.room.entity.RoomEntityRoom


@Dao
interface RoomDAORooms {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRoom(room: RoomEntityRoom):Long

    @Query("DELETE FROM roomentityroom WHERE id =(:roomId)")
    suspend fun deleteRoom(roomId: Long)

    @Query("DELETE FROM roomentityroom")
    suspend fun deleteAllRooms()

    @Query("SELECT * FROM roomentityroom")
    suspend fun getRoomList():List<RoomEntityRoom>?

    @Query("SELECT * FROM roomentityroom WHERE id = (:roomId)")
    suspend fun getRoom(roomId:Long): RoomEntityRoom?

    @Query("SELECT * FROM roomentityroom WHERE id = (:roomId)")
    fun getRoomLiveData(roomId:Long):LiveData<RoomEntityRoom>

    @Query("SELECT * FROM roomentityroom")
    fun getRoomListLiveData():LiveData<List<RoomEntityRoom>>
}