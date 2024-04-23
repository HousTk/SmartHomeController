package com.example.smartcontrollerv3.main.presentation.devices

import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.device.Device

import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceListUseCase
import com.example.domain.domain.usecase.ids.GetIdsList
import com.example.domain.domain.usecase.wifi.GetDeviceSettingsWifiUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DevicesViewModel(
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val getIdsList: GetIdsList,
    private val navigationController: NavigationController,
    ) : ViewModel(){

        private val jobList = ArrayList<Job>()

        var devicesList = MutableLiveData<ArrayList<Device>>()


    fun delete(deviceId:Int){
            try {
                val result = deleteDeviceUseCase.execute(id = deviceId)

                updateDevicesList()

            }catch (e:Exception){

            }

        }


    fun onClickAddDevice(){
        navigationController.navigateTo(R.id.addNewDeviceFragment)
    }

    fun onClickBack(){
        navigationController.navigateBack()
    }

    fun onClickMore(view:View){
        val menu = PopupMenu(view.context, view)

        menu.inflate(R.menu.devices)


        menu.show()
    }

    fun updateDevicesList(){

        val idsList = getIdsList.execute()
        val devicesListTmp = getDeviceListUseCase.execute(idsList)

        devicesList.value = devicesListTmp
    }

}