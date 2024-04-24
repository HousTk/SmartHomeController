package com.example.domain.domain.repository

import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.models.saveParams.SaveParamsRoom

interface AddressRepository {

    suspend fun saveAddress(address: SaveParamsAddress, usersUid: List<String>):String?

    suspend fun addAddress(address: Address):Boolean

    suspend fun deleteAddress(addressKey: String):Boolean

    suspend fun getAddress(addressKey:String): Address

    ////

    suspend fun addRoom(addressKey:String? = null, room: SaveParamsRoom):Long

    suspend fun addRoom(addressKey:String? = null, room: Room):Boolean

    suspend fun deleteRoom(addressKey:String? = null, roomId:Long):Boolean

    suspend fun getRoomList(addressKey:String? = null):List<Room>

    suspend fun getRoom(addressKey:String? = null, roomId: Long): Room

    suspend fun addDeviceIdToRoom(addressKey:String? = null, deviceId:Long, roomId: Long):Boolean

   // suspend fun createAllDevicesRoom(id: Long, name: String, icon: Int):Boolean

    suspend fun removeDeviceId(addressKey:String? = null, deviceId: Long, roomId: Long):Boolean

    suspend fun updateIcon(addressKey:String? = null, roomId: Long, icon:Int):Boolean


    ////


    suspend fun addDevice(addressKey:String? = null, savingParams: SaveParamsDevice):Long

    suspend fun addDevice(addressKey:String? = null, device: Device):Boolean

    suspend fun getDevice(addressKey:String? = null, id:Long): Device

    suspend fun deleteDevice(addressKey:String? = null, id: Long):Boolean

    suspend fun updateSettings(addressKey:String? = null, settings: Settings, id: Long):Boolean

    suspend fun updateDeviceName(addressKey:String? = null, name:String, id: Long):Boolean

    suspend fun updateDeviceIp(addressKey:String? = null, ip:String, id: Long):Boolean

    suspend fun updateDeviceType(addressKey:String? = null, type:Int, id: Long):Boolean

    suspend fun updateDeviceStatus(addressKey:String? = null, status:Boolean, id: Long):Boolean

    suspend  fun setUpdatingStatus(addressKey:String? = null, isUpdating:Boolean, id: Long):Boolean

}