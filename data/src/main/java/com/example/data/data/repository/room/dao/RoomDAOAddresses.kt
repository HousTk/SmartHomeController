package com.example.data.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.data.repository.room.entity.RoomEntityAddress

@Dao
interface RoomDAOAddresses {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(address: RoomEntityAddress)

    @Query("DELETE FROM address WHERE `key` =(:key)")
    fun deleteAddress(key:String)

    @Query("SELECT * FROM address WHERE `key` = (:addressKey)")
    suspend fun getAddress(addressKey: String): RoomEntityAddress?

}