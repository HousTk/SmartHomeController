package com.example.domain.domain.repository

import com.example.domain.domain.models.room.Room

interface RoomRepository {

    fun addToList(room:Room):Boolean

    fun removeFromList(roomPosition:Int):Boolean

    fun getRoomList():ArrayList<Room>

    fun getRoomFromList(roomPosition:Int):Room

    fun addDeviceIdToRoom(deviceId:Int, roomPosition: Int):Boolean

    fun addDeviceIdToAllDevicesRoom(deviceId: Int):Boolean

    fun removeDeviceId(deviceId: Int, roomPosition: Int):Boolean

    fun updateIcon(roomPosition: Int, icon:Int):Boolean

}