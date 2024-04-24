package com.example.smartcontrollerv3.main.presentation.addRoom


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.saveParams.SaveParamsRoom
import com.example.domain.domain.usecase.GetDevicesListInRoomUseCase
import com.example.domain.domain.usecase.rooms.AddNewRoomUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.domain.domain.utils.ROOM_NAME_MAX_SIZE
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.utils.ROOM_ICONS_ARRAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddRoomViewModel(
    private val navigationController: NavigationController,
    private val addNewRoomUseCase: AddNewRoomUseCase,
    private val getDevicesListInRoomUseCase: GetDevicesListInRoomUseCase
):ViewModel() {

    val icon = MutableLiveData<Int>(ROOM_ICONS_ARRAY[0])

    val devicesList = MutableLiveData<List<Device>>()

    fun getDeviceList(){

        CoroutineScope(Dispatchers.Main).launch{

            devicesList.value = getDevicesListInRoomUseCase.execute(roomId = ALLDEVICES_ROOM_ID.toLong())

        }

    }

    fun back(){

        navigationController.navigateBack()

    }

    fun selectIcon(ic:Int){
        icon.value = ic
    }

    fun save(name:String, devicesIdsList:ArrayList<Long>?){

        if(name != ""){

            if(name.length <= ROOM_NAME_MAX_SIZE){

                val room = SaveParamsRoom(
                    name = name,
                    icon = icon.value!!,
                    devicesIdsInRoom = devicesIdsList
                )

                    CoroutineScope(Dispatchers.IO).launch {

                        addNewRoomUseCase.execute(room)

                        withContext(Dispatchers.Main){

                            back()

                        }

                    }

            }else{
                throw Exception("Max name length $ROOM_NAME_MAX_SIZE symbols")
            }


        }else{
            throw Exception("Enter name!")
        }
    }
}