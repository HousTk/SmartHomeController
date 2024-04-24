package com.example.smartcontrollerv3.main.presentation.device

import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.data.data.repository.livedataTest.LiveDataRepository
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase
import com.example.domain.domain.usecase.wifi.WifiCallback
import com.example.domain.domain.usecase.wifi.WifiSendAndApplyUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "DeviceViewModel"

class DeviceViewModel(
    private val navigationController: NavigationController,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val removeDeviceFromRoomUseCase: RemoveDeviceFromRoomUseCase,
    private val wifiSendAndApplyUseCase: WifiSendAndApplyUseCase,
    private val liveDataRepository: LiveDataRepository
) : ViewModel() {


    var currentRoomId: Long = -1

    val device = MutableLiveData<Device>()

    fun applyArgs(deviceId: Long?, roomId: Long?) {

        if (deviceId != null && /*deviceId != (-1 as Long) && */ roomId != null) {

            currentRoomId = roomId

            CoroutineScope(Dispatchers.Main).launch {
                device.value = getDeviceUseCase.execute(deviceId)
            }
        } else {
            navigateBack()
        }
    }

    private val deviceObserver = Observer<RoomEntityDevice> {
        Log.i(TAG, "Device updated. $it")

        val deviceToAdd = Device(
            id = it.id!!,
            ip = it.ip,
            name = it.name,
            type = it.type,
            status = it.status,
            isUpdating = it.isUpdating,
            settings = it.settings
        )

        device.value = deviceToAdd
        return@Observer
    }

    fun init(lifecycleOwner: LifecycleOwner) {
        val liveData =
            liveDataRepository.getDeviceLiveData(addressKey = null, id = device.value!!.id)

        liveData.observe(lifecycleOwner, deviceObserver)

    }

    fun navigateBack() {
        navigationController.navigateBack()
    }

    fun onClickBack() {
        navigationController.navigateBack()
    }

    fun onClickSettings(view: View) {

        val menu = PopupMenu(view.context, view)

        val menuLayout = if (currentRoomId == ALLDEVICES_ROOM_ID.toLong()) {
            R.menu.device_all_devices_room
        } else {
            R.menu.device
        }

        menu.inflate(menuLayout)



        menu.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuDeviceDelete -> deleteDevice()
                R.id.menuDeviceEdit -> editDevice()
                R.id.menuDeviceRemove -> removeDevice()

                R.id.menuAllDevicesRoomDeviceDelete -> deleteDevice()
                R.id.menuAllDevicesRoomDeviceEdit -> editDevice()

            }

            return@setOnMenuItemClickListener true
        }


        menu.show()

    }

    private fun removeDevice() {

        CoroutineScope(Dispatchers.Main).launch {
            if (removeDeviceFromRoomUseCase.execute(
                    deviceId = device.value!!.id,
                    roomId = currentRoomId
                )
            ) {
                navigateBack()
            }
        }
    }

    private fun deleteDevice() {
        CoroutineScope(Dispatchers.Main).launch {
            if (deleteDeviceUseCase.execute(device.value!!.id)) {
                navigateBack()
            }
        }
    }

    private fun editDevice() {

    }


    fun turnOn() {
        CoroutineScope(Dispatchers.IO).launch {
            wifiSendAndApplyUseCase.turnOn(
                device = device.value!!,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }
                }
            )
        }
    }

    fun turnOff() {
        CoroutineScope(Dispatchers.IO).launch {
            wifiSendAndApplyUseCase.turnOff(
                device = device.value!!,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }
                }
            )
        }
    }

    fun changeBrightness(brightness: Int) {
        device.value!!.settings.brightness = brightness
        sendSettings()
    }

    fun changeColor(red: Int, green: Int, blue: Int) {
        device.value!!.settings.red = red
        device.value!!.settings.green = green
        device.value!!.settings.blue = blue
        sendSettings()
    }

    fun changeTargetTemp(targetTemp: Int) {

        device.value!!.settings.targetTemp = targetTemp
        sendSettings()

    }

    fun changeMoveTimeout(timeout: Int) {

        device.value!!.settings.movementSensorTimeout = timeout
        sendSettings()
    }

    private fun sendSettings() {

        CoroutineScope(Dispatchers.IO).launch {

            wifiSendAndApplyUseCase.settings(
                device = device.value!!,
                settings = device.value!!.settings,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }

                }
            )

        }
    }


}