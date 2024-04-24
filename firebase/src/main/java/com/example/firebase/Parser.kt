package com.example.firebase

import com.example.data.data.repository.models.FirebaseAddress
import com.example.data.data.repository.models.FirebaseDevice
import com.example.data.data.repository.models.FirebaseRoom

object Parser {

    fun parseAddress(data: Any?): FirebaseAddress {

        val hashData = data as HashMap<*, *>

        return FirebaseAddress(
            key = hashData["key"].toString(),
            name = hashData["name"].toString(),
            wifiSSID = hashData["wifiSSID"].toString()
        )

    }

    fun parseRoomsList(data: Any?): List<FirebaseRoom> {

        val list = ArrayList<FirebaseRoom>()

        if (data == null) return emptyList()

        val hashData = data as HashMap<*, *>

        val rooms = hashData["Rooms"]

        val roomsHash = if (rooms is ArrayList<*>) {

            rooms.associateBy { (it as HashMap<*, *>?)?.get("id") }

        } else {

            rooms as HashMap<*, *>? ?: return emptyList()

        }

        val roomsKeys = roomsHash.keys.toTypedArray()


        repeat(roomsHash.size) {

            val hashRoom = roomsHash[roomsKeys[it]] as HashMap<*, *>?

            if (hashRoom != null) {
                list.add(
                    FirebaseRoom(
                        id = hashRoom["id"] as Long,
                        name = hashRoom["name"].toString(),
                        icon = (hashRoom["icon"] as Long).toInt(),
                        devicesIdsInRoom = hashRoom["devicesIdsInRoom"] as ArrayList<Long>?
                            ?: ArrayList()
                    )
                )
            }

        }

        return list

    }

    fun parseRoom(data: Any?, roomId: Long): FirebaseRoom? {


        if (data == null) return null

        val hashData = data as HashMap<*, *>

        val rooms = hashData["Rooms"]

        val roomsHash = if (rooms is ArrayList<*>) {

            rooms.associateBy { (it as HashMap<*, *>?)?.get("id") }

        } else {

            rooms as HashMap<*, *>? ?: return null

        }

        val roomsKeys = roomsHash.keys.toTypedArray()



        repeat(roomsHash.size) {

            val hashRoom = roomsHash[roomsKeys[it]] as HashMap<*, *>?

            if (hashRoom != null) {
                if ((hashRoom["id"] as Long) == roomId) {
                    return FirebaseRoom(
                        id = hashRoom["id"] as Long,
                        name = hashRoom["name"].toString(),
                        icon = (hashRoom["icon"] as Long).toInt(),
                        devicesIdsInRoom = hashRoom["devicesIdsInRoom"] as ArrayList<Long>?
                            ?: ArrayList()

                    )
                }
            }

        }

        return null



    }

    fun parseDevicesList(data: Any?): List<FirebaseDevice> {


        val list = ArrayList<FirebaseDevice>()

        if (data == null) return emptyList()

        val hashData = data as HashMap<*, *>

        val devices = hashData["Devices"]

        val devicesHash = if (devices is ArrayList<*>) {

            devices.associateBy { (it as HashMap<*, *>?)?.get("id") }

        } else {

            devices as HashMap<*, *>? ?: return emptyList()

        }

        val devicesKeys = devicesHash.keys.toTypedArray()

        repeat(devicesHash.size) {

            val hashDevice = devicesHash[devicesKeys[it]] as HashMap<*, *>?

            if (hashDevice != null) {

                val hashSettings = hashDevice["settings"] as HashMap<*,*>
//                val settings = Settings(
//
//                    state = hashSettings["state"] as Boolean? ?: false,
//
//                    brightness = (hashSettings["brightness"] as Long?)?.toInt() ,
//                    red =  (hashSettings["red"] as Long?)?.toInt() ,
//                    green = (hashSettings["green"] as Long?)?.toInt() ,
//                    blue = (hashSettings["blue"] as Long?)?.toInt() ,
//
//                    targetTemp = (hashSettings["targetTemp"] as Long?)?.toInt() ,
//
//                    normalState = hashSettings["normalState"] as Boolean?
//
//                )

                list.add(
                    FirebaseDevice(
                        id = hashDevice["id"] as Long,
                        name = hashDevice["name"].toString(),
                        type = (hashDevice["type"] as Long).toInt(),
                        ip = hashDevice["ip"].toString(),
                        settings = hashSettings as HashMap<String, *>
                    )
                )

            }

        }

        return list

    }

}