package com.example.data.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.data.repository.room.entity.RoomEntityUser

@Dao
interface RoomDAOUsers {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: RoomEntityUser)

    @Query("SELECT * FROM users WHERE uid =(:userUid)")
    suspend fun getUser(userUid:String): RoomEntityUser?


}