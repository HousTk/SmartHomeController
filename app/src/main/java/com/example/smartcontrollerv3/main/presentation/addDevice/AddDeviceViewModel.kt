package com.example.smartcontrollerv3.main.presentation.addDevice

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.device.SavingDeviceParam
import com.example.domain.domain.usecase.SaveDeviceUseCase
import com.example.domain.domain.usecase.ids.GetNewId
import com.example.domain.domain.usecase.rooms.AddDeviceToRoomUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_POSITION
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class AddDeviceViewModel(
    private val getNewId: GetNewId,
    private val saveDeviceUseCase: SaveDeviceUseCase,
    private val navigationController: NavigationController,
    private val addDeviceToRoomUseCase: AddDeviceToRoomUseCase
):ViewModel() {


    fun save(name:String, ip:String, currentRoom:Int = -1){

        val newId = getNewId.execute()

        val savingDeviceParam = SavingDeviceParam(name = name, ip = ip,id = newId)

        val resultSavingDevice = saveDeviceUseCase.execute(savingDeviceParam)

        if(currentRoom != -1 && currentRoom != ALLDEVICES_ROOM_POSITION) {
            val resultDeviceAdd = addDeviceToRoomUseCase.execute(newId, currentRoom)
        }

        if (resultSavingDevice) {
            back()
        }
    }


    fun back(){
        navigationController.navigateBackTo(R.id.homeFragment)
    }

    fun onClickMore(view: View){

    }
}