package com.example.data.data.repository.room

import androidx.room.TypeConverter
import com.example.domain.domain.models.device.settings.Settings
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listStringToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListString(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun listLongToJson(value: List<Long>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListLong(value: String):List<Long>?{
        return if(value.isEmpty() || value == ""|| value == "null"){
            null
        }else{
            Gson().fromJson(value, Array<Long>::class.java).toList()
        }
    }

    @TypeConverter
    fun settingsToJson(value: Settings?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToSettings(value: String): Settings?{
        return if(value.isEmpty() || value == ""|| value == "null"){
            null
        }else{
            Gson().fromJson(value, Settings::class.java)
        }
    }
}