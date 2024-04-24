package com.example.data.data.repository.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.data.repository.room.dao.RoomDAOAddresses
import com.example.data.data.repository.room.dao.RoomDAODevices
import com.example.data.data.repository.room.dao.RoomDAORooms
import com.example.data.data.repository.room.entity.RoomEntityAddress
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.data.data.repository.room.Converters


@Database(
    version = 1,
    entities = [
        RoomEntityAddress::class,
        RoomEntityRoom::class,
        RoomEntityDevice::class
    ]
)
@TypeConverters(Converters::class)
abstract class RoomDatabaseAddress : RoomDatabase() {

    abstract fun addressInfoDao(): RoomDAOAddresses

    abstract fun roomsDao(): RoomDAORooms

    abstract fun devicesDao(): RoomDAODevices

}