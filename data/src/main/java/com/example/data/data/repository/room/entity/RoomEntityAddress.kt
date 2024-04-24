package com.example.data.data.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Address")
data class RoomEntityAddress(
    @PrimaryKey val key:String,
    @ColumnInfo val name:String,
    @ColumnInfo val wifiSSID:String,
)