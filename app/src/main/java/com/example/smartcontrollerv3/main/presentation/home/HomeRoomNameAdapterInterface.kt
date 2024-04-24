package com.example.smartcontrollerv3.main.presentation.home

interface HomeRoomNameAdapterInterface {
    fun onRoomClick(roomId:Long, roomPosition:Int)
    fun onDeleteRoom(roomId: Long)
    fun onCreateRoomClick()
}