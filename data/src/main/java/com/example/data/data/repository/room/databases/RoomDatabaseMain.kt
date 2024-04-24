package com.example.data.data.repository.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.data.repository.room.dao.RoomDAOUsers
import com.example.data.data.repository.room.entity.RoomEntityUser
import com.example.data.data.repository.room.Converters


@Database(
    version = 1,
    entities = [
        RoomEntityUser::class
    ]
)
@TypeConverters(Converters::class)
abstract class RoomDatabaseMain: RoomDatabase() {

    abstract fun usersDao(): RoomDAOUsers


}