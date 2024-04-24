package com.example.data.data.repository.utils


import com.example.data.data.repository.models.FirebaseAddress
import com.example.data.data.repository.models.FirebaseDevice
import com.example.data.data.repository.models.FirebaseRoom
import com.example.data.data.repository.models.FirebaseUser
import com.example.data.data.repository.room.entity.RoomEntityUser
import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.main.User
import com.google.gson.Gson

object DataConverter {

    fun addressFirebase(address: Address): FirebaseAddress {
        return FirebaseAddress(
            key = address.key,
            name = address.name,
            wifiSSID = address.wifiSSID
        )
    }

    fun addressFirebase(address: FirebaseAddress):Address{
        return Address(
            key = address.key,
            name = address.name,
            wifiSSID = address.wifiSSID
        )
    }

    fun userRoom(user:User):RoomEntityUser{
        return RoomEntityUser(
            uid = user.uid,
            name = user.name,
            email = user.email,
            addressesKeys = user.addressesKeys
        )
    }

    fun userRoom(user:RoomEntityUser):User{
        return User(
            uid = user.uid,
            name = user.name,
            email = user.email,
            addressesKeys = user.addressesKeys
        )
    }

    fun userFirebase(user: User): FirebaseUser {
        return FirebaseUser(
            uid = user.uid,
            name = user.name,
            email = user.email,
            addressesKeys = user.addressesKeys
        )
    }

    fun userFirebase(user: FirebaseUser):User{
        return User(
            uid = user.uid,
            name = user.name,
            email = user.email,
            addressesKeys = user.addressesKeys
        )
    }

    fun roomFirebase(room:Room): FirebaseRoom {
        return FirebaseRoom(
            id=room.id,
            name = room.name,
            icon = room.icon,
            devicesIdsInRoom = room.devicesIdsInRoom?.let { ArrayList(it) } ?: ArrayList()
        )
    }

    fun roomFirebase(room: FirebaseRoom):Room{
        return Room(
            id=room.id,
            name = room.name,
            icon = room.icon,
            devicesIdsInRoom = room.devicesIdsInRoom
        )
    }

    fun roomListFirebase(roomList: List<FirebaseRoom>):List<Room>{

        val listTmp = ArrayList<Room>()

        repeat(roomList.size){

            val room = roomList[it]

            listTmp.add( Room(
                id=room.id,
                name = room.name,
                icon = room.icon,
                devicesIdsInRoom = room.devicesIdsInRoom
            ))
        }

        return listTmp
    }

    fun deviceListFirebase(list: List<FirebaseDevice>): List<Device> {

        val listTmp = ArrayList<Device>()

        repeat(list.size) {

            val device = list[it]

            val hashSettings = device.settings

            val settings = Settings(

                state = hashSettings["state"] as Boolean? ?: false,

                brightness = (hashSettings["brightness"] as Long?)?.toInt() ,
                red =  (hashSettings["red"] as Long?)?.toInt() ,
                green = (hashSettings["green"] as Long?)?.toInt() ,
                blue = (hashSettings["blue"] as Long?)?.toInt() ,

                targetTemp = (hashSettings["targetTemp"] as Long?)?.toInt() ,

                normalState = hashSettings["normalState"] as Boolean?

            )

            listTmp.add( Device(
                name = device.name,
                ip = device.ip,
                id = device.id,
                type = device.type,
                settings = settings
            ))

        }

        return listTmp

    }
    fun deviceFirebase(device: Device): FirebaseDevice {

        val settings = device.settings

        val hashSettings = HashMap<String, Any?>()

        hashSettings["state"] = settings.state
        hashSettings["brightness"] = settings.brightness
        hashSettings["red"] = settings.red
        hashSettings["green"] = settings.green
        hashSettings["blue"] = settings.blue
        hashSettings["targetTemp"] = settings.targetTemp
        hashSettings["normalState"] = settings.normalState


        return FirebaseDevice(
            name = device.name,
            ip = device.ip,
            id = device.id,
            type = device.type,
            settings = hashSettings
        )

    }

    fun deviceFirebase(device: FirebaseDevice):Device{

        val hashSettings = device.settings

        val settings = Settings(

            state = hashSettings["state"] as Boolean? ?: false,

            brightness = (hashSettings["brightness"] as Long?)?.toInt() ,
            red =  (hashSettings["red"] as Long?)?.toInt() ,
            green = (hashSettings["green"] as Long?)?.toInt() ,
            blue = (hashSettings["blue"] as Long?)?.toInt() ,

            targetTemp = (hashSettings["targetTemp"] as Long?)?.toInt() ,

            normalState = hashSettings["normalState"] as Boolean?

        )

        return Device(
            name = device.name,
            ip = device.ip,
            id = device.id,
            type = device.type,
            settings = settings
        )

    }



}