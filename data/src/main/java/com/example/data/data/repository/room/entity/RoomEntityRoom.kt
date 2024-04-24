package com.example.data.data.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntityRoom(
    @PrimaryKey(autoGenerate = true) val id:Long? = null,
    @ColumnInfo val name:String,
    @ColumnInfo val icon:Int,
    @ColumnInfo val devicesIdsInRoom: List<Long>?
)