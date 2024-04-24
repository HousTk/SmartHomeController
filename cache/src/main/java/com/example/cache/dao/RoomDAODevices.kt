package com.example.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.data.repository.room.entity.RoomEntityDevice

@Dao
interface RoomDAODevices {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDevice(device: RoomEntityDevice):Long

    @Query("DELETE FROM roomentitydevice WHERE id =(:deviceId)")
    suspend fun deleteDevice(deviceId:Long)

    @Query("DELETE FROM roomentitydevice")
    suspend fun deleteAllDevices()

    @Query("SELECT * FROM roomentitydevice WHERE id = (:deviceId)")
    suspend fun getDevice(deviceId:Long): RoomEntityDevice?

    @Query("SELECT * FROM roomentitydevice WHERE id = (:deviceId)")
    fun getDeviceLiveData(deviceId: Long):LiveData<RoomEntityDevice>
}