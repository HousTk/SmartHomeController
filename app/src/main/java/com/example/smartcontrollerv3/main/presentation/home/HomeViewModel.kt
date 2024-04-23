package com.example.smartcontrollerv3.main.presentation.home


import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.address.Address
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.models.room.Room
import com.example.domain.domain.usecase.DeleteDeviceUseCase

import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.address.GetCurrentAddressUseCase
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase

import com.example.domain.domain.usecase.rooms.RemoveRoomUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceSettingsWifiUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceStateWifiUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceTypeWifiUseCase
import com.example.domain.domain.usecase.wifi.SendTurnOffWifiUseCase
import com.example.domain.domain.usecase.wifi.SendTurnOnWifiUseCase
import com.example.domain.domain.usecase.wifi.WifiCallback
import com.example.domain.domain.usecase.wifi.WifiJobList
import com.example.domain.domain.utils.ALLDEVICES_ROOM_POSITION
import com.example.domain.domain.utils.TAG
import com.example.smartcontrollerv3.R

import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.presentation.device.DeviceFragment


class HomeViewModel(
    private val navigationController: NavigationController,
    private val getRoomListUseCase: GetRoomListUseCase,
    private val removeRoomUseCase: RemoveRoomUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val removeDeviceFromRoomUseCase: RemoveDeviceFromRoomUseCase,
    private val wifiJobList: WifiJobList,
    private val getDeviceTypeWifiUseCase: GetDeviceTypeWifiUseCase,
    private val sendTurnOnUseCase: SendTurnOnWifiUseCase,
    private val sendTurnOffWifiUseCase: SendTurnOffWifiUseCase,
    private val getDeviceSettingsWifiUseCase: GetDeviceSettingsWifiUseCase,
    private val getDeviceStateWifiUseCase: GetDeviceStateWifiUseCase,
    private val getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase
) : ViewModel() {

    var roomList = MutableLiveData<ArrayList<Room>>()
    var selectedRoomPosition = MutableLiveData<Int>(0)

    var currentAddress = MutableLiveData<Address>()

    val devicesListInSelectedRoom = MutableLiveData<ArrayList<Device>>()

    fun onCreateView() {
        updateRoomList()
        updateAddress()
        updateDevices()
        connectToDevices()
    }

    fun onDestroyView() {
        wifiJobList.clear()
    }

    fun navigateToDevices() {
        navigationController.navigateTo(R.id.devicesFragment)
    }


    fun onClickMore(view: View) {
        showMoreMenu(view)
    }

    fun onClickAddress(view: View) {
        navigationController.navigateTo(R.id.addressesFragment)
    }

    fun onClickRoom(position: Int) {

        selectedRoomPosition.value = position

        updateDevices()

        connectToDevices()

    }

    fun onClickAddRoom() {
        navigationController.navigateTo(R.id.addRoomFragment)
    }

    fun onClickAddDevice() {

        navigationController.navigateWithArgs(
            key = "CurrentRoom",
            args = selectedRoomPosition.value!!,
            destinationPageId = R.id.addDeviceToRoomFirstPageFragment
        )

    }


    fun connectToDevices() {

        val idsInRoom = roomList.value!![selectedRoomPosition.value!!].devicesIdsInRoom

        Log.i(TAG, "Connecting to devices...")

        repeat(idsInRoom.size) {

            val deviceId = idsInRoom[it]

            val device = getDeviceUseCase.execute(id = deviceId)

            Log.i(TAG, "Connecting to ${device.ip}")

            if (device.type == -1) {
                getDeviceTypeWifiUseCase.execute(
                    object : WifiCallback {
                        override fun requestComplete(isSuccess: Boolean) {
                            Log.i(TAG, "Got type of ${device.ip} ")
                            getDeviceState(deviceId)
                        }

                        override fun updateDevice() {
                            updateDevices()
                        }

                    },
                    deviceId
                )
            } else {
                getDeviceState(deviceId)
            }
        }
    }

    fun getDeviceState(deviceId: Int) {

        getDeviceStateWifiUseCase.execute(
            object : WifiCallback {
                override fun requestComplete(isSuccess: Boolean) {
                    Log.i(TAG, "Got State")
                }

                override fun updateDevice() {
                    updateDevices()
                }

            },
            deviceId = deviceId
        )
    }

    fun getDeviceSettings(deviceId: Int) {

        getDeviceSettingsWifiUseCase.execute(
            object : WifiCallback {
                override fun requestComplete(isSuccess: Boolean) {
                    Log.i(TAG, "Got settings")
                }

                override fun updateDevice() {
                    updateDevices()
                }

            },
            deviceId = deviceId
        )

    }

    fun sendTurnOnToDevice(deviceId: Int) {
        Log.i(TAG, "Sending turn on...")

        sendTurnOnUseCase.execute(
            object : WifiCallback {
                override fun requestComplete(isSuccess: Boolean) {

                }

                override fun updateDevice() {
                    updateDevices()
                }

            },
            deviceId = deviceId
        )
    }

    fun sendTurnOffToDevice(deviceId: Int) {
        Log.i(TAG, "Sending turn off...")

        sendTurnOffWifiUseCase.execute(
            object : WifiCallback {
                override fun requestComplete(isSuccess: Boolean) {

                }

                override fun updateDevice() {
                    updateDevices()
                }

            },
            deviceId = deviceId
        )
    }

    fun updateDevices() {
        devicesListInSelectedRoom.value = getDevices()
    }

    fun updateRoomList() {
        roomList.value = getRoomListUseCase.execute()
    }

    fun updateAddress() {
        currentAddress.value = getCurrentAddressUseCase.execute()
    }

    fun deleteRoom(roomPosition: Int) {
        removeRoomUseCase.execute(roomPosition = roomPosition)

        updateRoomList()
    }

    fun deleteDevice(deviceId: Int) {

        if (selectedRoomPosition.value!! == ALLDEVICES_ROOM_POSITION) {

            deleteDeviceUseCase.execute(id = deviceId)

        } else {
            removeDeviceFromRoomUseCase.execute(
                deviceId = deviceId,
                roomPosition = selectedRoomPosition.value!!
            )
        }

        updateDevices()
    }


    fun onDeviceClick(deviceId: Int) {

        navigationController.putArgsToBundle(
            bundleKey = "ToDevice",
            argKey = DeviceFragment().KEY_DEVICE_ID,
            arg = deviceId
        )

        navigationController.putArgsToBundle(
            bundleKey = "ToDevice",
            argKey = DeviceFragment().KEY_ROOM_POSITION,
            arg = selectedRoomPosition.value!!
        )

        navigationController.navigateToWithBundle(
            bundleKey = "ToDevice",
            destinationPageId = R.id.deviceFragment
        )

    }

    private fun getDevices(): ArrayList<Device> {
        if (selectedRoomPosition.value == null) {
            throw Exception("There is no room selected")
        }

        val list = ArrayList<Device>()

        val room = getRoomListUseCase.execute()[selectedRoomPosition.value!!]

        repeat(room.devicesIdsInRoom.size) {
            list.add(getDeviceUseCase.execute(room.devicesIdsInRoom[it]))
        }

        return list

    }

    private fun showMoreMenu(view: View) {
        val menu = PopupMenu(view.context, view)

        menu.inflate(R.menu.home)

        menu.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuHomeDevices -> {
                    navigateToDevices()
                }

            }

            return@setOnMenuItemClickListener true
        }


        menu.show()
    }
}