package com.example.data.data.repository.room

import androidx.lifecycle.LiveData
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.models.main.User
import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.models.saveParams.SaveParamsRoom

interface CacheStorageInterface {

//    suspend fun initMain()
//
//    suspend fun initAddress(addressKey: String)

    //user info


    //suspend fun isUserInDB(userUid: String):Boolean

    suspend fun addUser(user: User):Boolean

    suspend fun getUser(userUid: String): User?

    suspend  fun getAvailableAddressesKeysList(userUid:String):List<String>

    suspend  fun addAvailableAddressKey(addressKey: String, userUid: String):Boolean

    suspend fun removeAvailableAddressKey(addressKey: String, userUid: String):Boolean


    //addresses

    //suspend fun isAddressInDB(addressKey: String):Boolean

    suspend fun addAddress(address: Address): Boolean

    suspend fun deleteAddress(addressKey: String): Boolean

    suspend fun getAddress(addressKey: String): Address?


    //rooms

    //fun getRoomListLiveData():LiveData<List<RoomEntityRoom>>

    suspend fun addRoom(addressKey: String, room: SaveParamsRoom): Long

    suspend fun addRoom(addressKey: String, room: Room): Boolean

    suspend fun deleteRoom(addressKey: String, roomId: Long): Boolean

    suspend fun deleteAllRooms(addressKey: String, ):Boolean

    suspend fun getRoomList(addressKey: String, ): List<Room>?

    suspend fun getRoom(addressKey: String, roomId: Long): Room?

    fun getRoomListLiveData(addressKey: String):LiveData<List<RoomEntityRoom>>

    fun getRoomLiveData(addressKey: String, id: Long):LiveData<RoomEntityRoom>

    //devices

    fun getDeviceLiveData(addressKey: String, deviceId:Long):LiveData<RoomEntityDevice>

    suspend fun addDevice(addressKey: String, device: SaveParamsDevice): Long

    suspend fun addDevice(addressKey: String, device: Device): Boolean

    suspend fun getDevice(addressKey: String, id: Long): Device?

    suspend fun deleteDevice(addressKey: String, id: Long): Boolean

    suspend fun deleteAllDevice(addressKey: String, ):Boolean

    suspend fun getDevicesIdsList(addressKey: String, ):List<Long>


}