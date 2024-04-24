package com.example.smartcontrollerv3.main.presentation.home


import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.data.data.repository.livedataTest.LiveDataRepository
import com.example.data.data.repository.room.entity.RoomEntityDevice
import com.example.data.data.repository.room.entity.RoomEntityRoom
import com.example.domain.domain.callbacks.OnCompleteCallback
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.GetDevicesListInRoomUseCase
import com.example.domain.domain.usecase.address.GetAddressUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromHomeUseCase
import com.example.domain.domain.usecase.rooms.DeleteRoomUseCase
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.rooms.GetRoomUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase
import com.example.domain.domain.usecase.settings.GetSelectedAddressKeyUseCase
import com.example.domain.domain.usecase.wifi.WifiCallback
import com.example.domain.domain.usecase.wifi.WifiGetAndApplyUseCase
import com.example.domain.domain.usecase.wifi.WifiSendAndApplyUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.smartcontrollerv3.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "HomeViewModel"
class HomeViewModel(
    private val navigateFromHomeUseCase: NavigateFromHomeUseCase,
    private val getRoomListUseCase: GetRoomListUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val removeDeviceFromRoomUseCase: RemoveDeviceFromRoomUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val getRoomUseCase: GetRoomUseCase,
    private val getSelectedAddressKeyUseCase: GetSelectedAddressKeyUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getDevicesListInRoomUseCase: GetDevicesListInRoomUseCase,
    private val wifiGetAndApplyUseCase: WifiGetAndApplyUseCase,
    private val wifiSendAndApplyUseCase: WifiSendAndApplyUseCase,
    private val liveDataRepository: LiveDataRepository
) : ViewModel() {


    val roomList = MutableLiveData<ArrayList<Room>>()

    val selectedRoomId = MutableLiveData<Long>(0)

    var selectedRoomPosition = MutableLiveData<Int>(0)

    var currentAddress = MutableLiveData<Address>()

    val devicesListInSelectedRoom = MutableLiveData<ArrayList<Device>>()


    private val wifiJobList = ArrayList<Job>()
    private var roomsListLiveData: LiveData<List<RoomEntityRoom>>? = null
    private val devicesLiveDataMap = HashMap<Long, LiveData<RoomEntityDevice>>()

    private lateinit var lifecycleOwner: LifecycleOwner


    fun onCreateView() {
        CoroutineScope(Dispatchers.IO).launch{
            getAddress()
            getRoomList()
            getDevices()
            connectToDevices()
        }
    }

    fun setLifecycleOwner(p: LifecycleOwner) {
        lifecycleOwner = p
    }


    fun onStopView() {
        repeat(wifiJobList.size) {
            wifiJobList[it].cancel()
        }
        wifiJobList.clear()
    }

    fun refresh(callback: OnCompleteCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            getDevices()
            connectToDevices()
            withContext(Dispatchers.Main) {
                callback.onSuccess()
            }
        }
    }

    fun navigateToDevices() {
        TODO()
    }

    fun onClickProfile() {
        navigateFromHomeUseCase.toProfile()
    }

    fun onClickMore(view: View) {
        showMoreMenu(view)
    }

    fun onClickAddress(view: View) {
        navigateFromHomeUseCase.toAddresses()
    }

    fun onClickRoom(roomId: Long, roomPosition: Int) {

        selectedRoomId.value = roomId

        selectedRoomPosition.value = roomPosition

        CoroutineScope(Dispatchers.IO).launch {
            getDevices()
            connectToDevices()
        }

        //connectToDevices()

    }

    fun onClickAddRoom() {
        navigateFromHomeUseCase.toAddRoom()
    }

    fun onClickAddDevice() {

        val args = selectedRoomId.value!!

        navigateFromHomeUseCase.toAddDevice(roomId = args)

    }


    private fun connectToDevices() {

        CoroutineScope(Dispatchers.IO).launch() {

            val devices = devicesListInSelectedRoom.value

            repeat(devices?.size ?: 0) {

                val job = CoroutineScope(Dispatchers.IO).launch {

                    wifiGetAndApplyUseCase.type(
                        device = devices!![it],
                        callback = object : WifiCallback {
                            override fun requestComplete(isSuccess: Boolean) {
                                getDeviceSettings(devices[it])
                            }

                        }
                    )

                }

                wifiJobList.add(job)

            }

        }


    }


    fun getDeviceSettings(device: Device) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            wifiGetAndApplyUseCase.settings(
                device = device,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }

                }
            )
        }
        wifiJobList.add(job)
    }

    fun sendTurnOnToDevice(deviceId: Long) {
        Log.i(TAG, "Sending turn on...")

        var device:Device? = null

        val listTmp = devicesListInSelectedRoom.value ?: emptyList()

        repeat(listTmp.size) {
            val deviceTmp = listTmp[it]
            if(deviceTmp.id == deviceId) device = deviceTmp
        }

        if(device == null) throw Exception("There is no device with id $deviceId")

        val job = CoroutineScope(Dispatchers.IO).launch {

            wifiSendAndApplyUseCase.turnOn(
                device = device!!,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }

                }
            )

        }

        wifiJobList.add(job)

    }

    fun sendTurnOffToDevice(deviceId: Long) {
        Log.i(TAG, "Sending turn off...")

        var device:Device? = null

        val listTmp = devicesListInSelectedRoom.value ?: emptyList()

        repeat(listTmp.size) {
            val deviceTmp = listTmp[it]
            if(deviceTmp.id == deviceId) device = deviceTmp
        }

        if(device == null) throw Exception("There is no device with id $deviceId")

        val job = CoroutineScope(Dispatchers.IO).launch {

            wifiSendAndApplyUseCase.turnOff(
                device = device!!,
                callback = object : WifiCallback {
                    override fun requestComplete(isSuccess: Boolean) {

                    }
                }
            )

        }

        wifiJobList.add(job)
    }


    private suspend fun getAddress() {
        val address = getAddressUseCase.execute(getSelectedAddressKeyUseCase.execute())

        withContext(Dispatchers.Main) {
            currentAddress.value = address
        }
    }

    private suspend fun getRoomList() {
        val list = getRoomListUseCase.execute()

        withContext(Dispatchers.Main) {
            //roomList.value = ArrayList(list)

            observeRoomList()
        }
    }

    private suspend fun getDevices() {

        withContext(Dispatchers.Main) {
            devicesLiveDataMap.forEach {
                devicesLiveDataMap[it.key]?.removeObserver(deviceObserver)
            }
            devicesLiveDataMap.clear()
        }

        if (selectedRoomId.value == null) {
            throw Exception("There is no room selected")
        }

        val roomId = selectedRoomId.value!!

        val list = getDevicesListInRoomUseCase.execute(roomId)

        withContext(Dispatchers.Main) {
            devicesListInSelectedRoom.value = ArrayList(list)

            repeat(list.size) {
                val device = list[it]
                observeDevice(id = device.id)
            }
        }
    }

    private val deviceObserver = Observer<RoomEntityDevice>{

        Log.i(TAG, "Device updated. $it")

        val curList = (devicesListInSelectedRoom.value ?: ArrayList())

        val deviceToAdd =  Device(
            id = it.id!!,
            ip = it.ip,
            name = it.name,
            type = it.type,
            status = it.status,
            isUpdating = it.isUpdating,
            settings = it.settings
        )

        repeat(curList.size) {iteration ->
            val device = curList[iteration]

            if (device.id == it.id) {
                curList[iteration] = deviceToAdd
                devicesListInSelectedRoom.value = curList
                return@Observer
            }
        }

        curList.add(deviceToAdd)
        devicesListInSelectedRoom.value = curList
        return@Observer

    }
    private fun observeDevice(id: Long) {
        val livedata = liveDataRepository.getDeviceLiveData(addressKey = null, id = id)

        livedata.observe(lifecycleOwner,deviceObserver)

        devicesLiveDataMap[id] = livedata
    }

    private fun observeRoomList() {
        val liveData = liveDataRepository.getRoomListLiveData(addressKey = null)

        liveData.observe(lifecycleOwner) { newRoomList ->
            val roomListTmp = ArrayList<Room>()

            repeat(newRoomList.size) {
                val roomTmp = newRoomList[it]
                roomListTmp.add(
                    Room(
                        id = roomTmp.id!!,
                        name = roomTmp.name,
                        icon = roomTmp.icon,
                        devicesIdsInRoom = roomTmp.devicesIdsInRoom
                    )
                )
            }
            roomList.value = roomListTmp
        }
    }

    fun deleteRoom(roomId: Long) {

        CoroutineScope(Dispatchers.IO).launch {

            deleteRoomUseCase.execute(roomId = roomId)

            if (selectedRoomId.value!! == roomId) {

                withContext(Dispatchers.Main) {

                    selectedRoomId.value = ALLDEVICES_ROOM_ID.toLong()
                    selectedRoomPosition.value = 0
                    getDevices()

                }

            }

            //getRoomList()
        }


    }

    fun deleteOrRemoveDevice(deviceId: Long) {

        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                devicesLiveDataMap[deviceId]?.removeObserver(deviceObserver)
                devicesLiveDataMap.remove(deviceId)

                val listTmp = devicesListInSelectedRoom.value ?: throw Exception()
                repeat(listTmp.size){
                    if(listTmp[it].id == deviceId) {
                        listTmp.removeAt(it)
                        devicesListInSelectedRoom.value = listTmp
                    }
                }
            }

            if (selectedRoomId.value!! == ALLDEVICES_ROOM_ID.toLong()) {
                deleteDeviceUseCase.execute(id = deviceId)

            } else {
                removeDeviceFromRoomUseCase.execute(
                    deviceId = deviceId,
                    roomId = selectedRoomId.value!!
                )
            }
        }
    }

    fun deleteDevice(deviceId: Long) {

        CoroutineScope(Dispatchers.IO).launch {


            deleteDeviceUseCase.execute(id = deviceId)


            getDevices()
        }
    }

    fun removeDevice(deviceId: Long) {

        CoroutineScope(Dispatchers.IO).launch {

            if (selectedRoomId.value!! != ALLDEVICES_ROOM_ID.toLong()) {

                removeDeviceFromRoomUseCase.execute(
                    deviceId = deviceId,
                    roomId = selectedRoomId.value!!
                )

            }

            getDevices()
        }
    }


    fun onDeviceClick(deviceId: Long) {

        navigateFromHomeUseCase.toDevice(
            deviceId = deviceId,
            roomId = selectedRoomId.value!!
        )

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