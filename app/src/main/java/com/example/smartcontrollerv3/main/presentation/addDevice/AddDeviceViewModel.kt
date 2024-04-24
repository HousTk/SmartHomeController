package com.example.smartcontrollerv3.main.presentation.addDevice

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.saveParams.SaveParamsDevice
import com.example.domain.domain.usecase.SaveDeviceUseCase

import com.example.domain.domain.usecase.rooms.AddDeviceToRoomUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDeviceViewModel(
    private val saveDeviceUseCase: SaveDeviceUseCase,
    private val navigationController: NavigationController,
    private val addDeviceToRoomUseCase: AddDeviceToRoomUseCase
):ViewModel() {

    private var roomId:Long = -1
    fun initVm(currentRoomId:Long){

        roomId = currentRoomId

    }

    fun save(name:String, ip:String){

        val saveParamsDevice = SaveParamsDevice(name = name, ip = ip, type = -1)

        CoroutineScope(Dispatchers.IO).launch{

            val deviceId = saveDeviceUseCase.execute(saveParamsDevice)

            if(roomId != ALLDEVICES_ROOM_ID.toLong()) {

                addDeviceToRoomUseCase.execute(deviceId = deviceId, roomId)

            }

            withContext(Dispatchers.Main){

                back()

            }


        }

    }


    fun back(){
        navigationController.navigateBackTo(R.id.homeFragment)
    }

    fun onClickMore(view: View){

    }
}