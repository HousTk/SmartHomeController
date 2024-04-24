package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.addConnectedDevice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.usecase.GetDevicesListInRoomUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromADTRUseCase
import com.example.domain.domain.usecase.rooms.AddDeviceToRoomUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDeviceToRoomViewModel(
    private val navigateFromADTRUseCase: NavigateFromADTRUseCase,
    private val addDeviceToRoomUseCase: AddDeviceToRoomUseCase,
    private val getDevicesListInRoomUseCase: GetDevicesListInRoomUseCase
) : ViewModel() {

    val selectedDevices = MutableLiveData<ArrayList<Long>>()

    val devicesList = MutableLiveData<List<Device>>()


    private var roomId:Long = -1

    init {
        selectedDevices.value = ArrayList()
    }

    fun initVM(currentRoomId:Long){
        roomId = currentRoomId
        CoroutineScope(Dispatchers.Main).launch {

            val allDevices = getDevicesListInRoomUseCase.execute(roomId = ALLDEVICES_ROOM_ID.toLong())

            val devicesInThisRoom = getDevicesListInRoomUseCase.execute(roomId = roomId)

            val resultList = ArrayList<Device>()

            repeat(allDevices.size){

                val device = allDevices[it]

                if(!devicesInThisRoom.contains(device)){
                    resultList.add(device)
                }

            }

            devicesList.value = resultList
        }
    }
    fun selectDevice(deviceId: Long) {

        val list = selectedDevices.value!!

        if(list.contains(deviceId)){
            list.remove(deviceId)
        }else{
            list.add(deviceId)
        }

        selectedDevices.value = list

    }

    fun save(){

        CoroutineScope(Dispatchers.IO).launch {

            addDeviceToRoomUseCase.list(list = selectedDevices.value!!, roomId = roomId)

            withContext(Dispatchers.Main){

                navigateFromADTRUseCase.back()

            }

        }





    }


}