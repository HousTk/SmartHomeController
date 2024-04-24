package com.example.data.data.repository.livedataTest

import androidx.lifecycle.LiveData
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.domain.domain.models.main.Device

interface LiveDataRepository {

    fun getRoomListLiveData(addressKey: String? = null):LiveData<List<RoomEntityRoom>>

    fun getRoomLiveData(addressKey: String? = null, id:Long):LiveData<RoomEntityRoom>

    fun getDeviceLiveData(addressKey:String? = null, id:Long):LiveData<RoomEntityDevice>

}