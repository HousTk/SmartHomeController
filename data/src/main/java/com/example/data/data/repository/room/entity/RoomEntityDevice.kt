package com.example.data.data.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.domain.models.device.settings.Settings

@Entity
data class RoomEntityDevice(
    @PrimaryKey(autoGenerate = true)  val id:Long? = null,
    @ColumnInfo val name:String,
    @ColumnInfo val ip:String,
    @ColumnInfo val type:Int,
    @ColumnInfo val status:Boolean = false,
    //@ColumnInfo val state:Boolean = false,
    @ColumnInfo val isUpdating:Boolean = false,
    @ColumnInfo val settings: Settings
)