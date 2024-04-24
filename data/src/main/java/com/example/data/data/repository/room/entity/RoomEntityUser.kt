package com.example.data.data.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class RoomEntityUser(
    @PrimaryKey val uid:String,
    @ColumnInfo val name:String,
    @ColumnInfo val email:String,
    @ColumnInfo val addressesKeys: List<String>
)