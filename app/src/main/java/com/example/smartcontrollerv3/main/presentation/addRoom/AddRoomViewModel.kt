package com.example.smartcontrollerv3.main.presentation.addRoom


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.usecase.GetDeviceListUseCase
import com.example.domain.domain.usecase.ids.GetIdsList
import com.example.domain.domain.usecase.rooms.AddNewRoomUseCase
import com.example.domain.domain.utils.ROOM_NAME_MAX_SIZE
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.utils.ROOM_ICONS_ARRAY
import java.lang.Exception

class AddRoomViewModel(
    private val navigationController: NavigationController,
    private val addNewRoomUseCase: AddNewRoomUseCase,
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val getIdsList: GetIdsList
):ViewModel() {

    val icon = MutableLiveData<Int>(ROOM_ICONS_ARRAY[0])

    fun getDeviceList():ArrayList<Device>{
        return getDeviceListUseCase.execute(getIdsList.execute())
    }

    fun back(){

        navigationController.navigateBack()

    }

    fun selectIcon(ic:Int){
        icon.value = ic
    }

    fun save(name:String, devicesIdsList:ArrayList<Int>?){

        if(name != ""){

            if(name.length <= ROOM_NAME_MAX_SIZE){

                if(devicesIdsList == null){

                    val result = addNewRoomUseCase.execute(roomName = name, icon = icon.value!!)

                    if (result){
                        back()
                    }


                }else if(devicesIdsList.isNotEmpty()){

                    val result = addNewRoomUseCase.execute(roomName = name, icon = icon.value!!, deviceIdsArrayList = devicesIdsList)

                    if (result){
                        back()
                    }

                }else{
                    throw Exception("Select at least 1 device!")
                }


            }else{
                throw Exception("Max name length $ROOM_NAME_MAX_SIZE symbols")
            }


        }else{
            throw Exception("Enter name!")
        }
    }
}