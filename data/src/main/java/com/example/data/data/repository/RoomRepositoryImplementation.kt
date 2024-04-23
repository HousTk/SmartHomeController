package com.example.data.data.repository

import android.content.Context
import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.models.room.Room
import com.example.domain.domain.repository.RoomRepository
import com.example.domain.domain.utils.ALLDEVICES_ROOM_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFS_NAME = "Rooms"
private const val SHARED_PREFS_KEY_ROOMLIST = "RoomList"

class RoomRepositoryImplementation(
    context : Context,
    private val settingsStorageInterface: SettingsStorageInterface
    ):RoomRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()
    private val gson = Gson()


    private fun getKey():String{
        return "$SHARED_PREFS_KEY_ROOMLIST${settingsStorageInterface.getSelectedAddress()}"
    }

    override fun addToList(room: Room): Boolean {
        val roomList = getRoomList()
        roomList.add(room)

        saveRoomList(roomList)

        return true
    }

    override fun removeFromList(roomPosition: Int): Boolean {
        val roomList = getRoomList()

        try {
            roomList.removeAt(roomPosition)

            saveRoomList(roomList)

            return true
        }catch (e:Exception){

            return false

        }

    }

    override fun getRoomList(): ArrayList<Room> {

        val jsonRoomList = sharedPreferences.getString(getKey(), null)

        val typeIds: Type = object: TypeToken<ArrayList<Room>>(){}.type

        val roomList = gson.fromJson<Any>(jsonRoomList, typeIds) as ArrayList<Room>?

        return roomList ?: ArrayList()
    }

    override fun getRoomFromList(roomPosition: Int): Room {
        val roomList = getRoomList()

        return roomList[roomPosition]
    }

    override fun addDeviceIdToRoom(deviceId: Int, roomPosition: Int): Boolean {
        val roomList = getRoomList()

        roomList[roomPosition].devicesIdsInRoom.add(deviceId)

        return saveRoomList(roomList)

    }

    override fun addDeviceIdToAllDevicesRoom(deviceId: Int): Boolean {
        val roomList = getRoomList()

        var roomPosition:Int = -1

        repeat(roomList.size){
            if(roomList[it].name == ALLDEVICES_ROOM_NAME){
                roomPosition = it
            }
        }

        roomList[roomPosition].devicesIdsInRoom.add(deviceId)

        return saveRoomList(roomList)
    }

    override fun removeDeviceId(deviceId: Int, roomPosition: Int): Boolean {
        val roomList = getRoomList()

        try {
            roomList[roomPosition].devicesIdsInRoom.remove(deviceId)

            saveRoomList(roomList)

            return true
        }catch (e:Exception){
            return false
        }
    }

    override fun updateIcon(roomPosition: Int, icon: Int): Boolean {
        val roomList = getRoomList()

        roomList[roomPosition].icon = icon

        return saveRoomList(roomList)
    }


    private fun saveRoomList(roomList:ArrayList<Room>):Boolean{

        val jsonIdsList:String = gson.toJson(roomList)

        spEditor.putString(getKey(),jsonIdsList).apply()

        return true
    }

}