package com.example.smartcontrollerv3.main.presentation.device

import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_POSITION
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class DeviceViewModel(
    private val navigationController: NavigationController,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val removeDeviceFromRoomUseCase: RemoveDeviceFromRoomUseCase
):ViewModel() {

    lateinit var device:Device

    var currentroomPosition:Int = -1

    fun applyArgs(deviceId:Int?, roomPosition:Int?){

        if(deviceId != null && deviceId != -1 && roomPosition != null){

            device = getDeviceUseCase.execute(deviceId)

            currentroomPosition = roomPosition
        }else{
           navigateBack()
        }
    }

    fun navigateBack(){
        navigationController.navigateBack()
    }

    fun onClickBack(){
        navigationController.navigateBack()
    }

    fun onClickMore(view: View){

        val menu = PopupMenu(view.context, view)

        val menuLayout = if(currentroomPosition == ALLDEVICES_ROOM_POSITION){
            R.menu.device_all_devices_room
        }else{
            R.menu.device
        }

        menu.inflate(menuLayout)



        menu.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuDeviceDelete ->deleteDevice()
                R.id.menuDeviceEdit -> editDevice()
                R.id.menuDeviceRemove -> removeDevice()

                R.id.menuAllDevicesRoomDeviceDelete -> deleteDevice()
                R.id.menuAllDevicesRoomDeviceEdit -> editDevice()

            }

            return@setOnMenuItemClickListener true
        }


        menu.show()

    }

    private fun removeDevice(){

        if(removeDeviceFromRoomUseCase.execute(
            deviceId = device.id,
            roomPosition = currentroomPosition
        )){
            navigateBack()
        }
    }

    private fun deleteDevice(){
        if(deleteDeviceUseCase.execute(device.id)){
            navigateBack()
        }
    }

    private fun editDevice(){

    }
}