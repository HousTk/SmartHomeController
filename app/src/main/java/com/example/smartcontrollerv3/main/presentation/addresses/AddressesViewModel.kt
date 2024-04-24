package com.example.smartcontrollerv3.main.presentation.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.usecase.GetDevicesListInRoomUseCase
import com.example.domain.domain.usecase.address.GetAddressUseCase
import com.example.domain.domain.usecase.user.GetAvailableAddressesKeysUseCase
import com.example.domain.domain.usecase.address.RemoveAddressUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromAddressesUseCase
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.settings.ChangeSelectedAddressUseCase
import com.example.domain.domain.usecase.settings.GetSelectedAddressKeyUseCase
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddressesViewModel(
    private val getAvailableAddressesKeysUseCase: GetAvailableAddressesKeysUseCase,
    private val getSelectedAddressKeyUseCase: GetSelectedAddressKeyUseCase,
    private val changeSelectedAddressUseCase: ChangeSelectedAddressUseCase,
    private val getRoomListUseCase: GetRoomListUseCase,
    private val removeAddressUseCase: RemoveAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getDevicesListInRoomUseCase: GetDevicesListInRoomUseCase,

    private val navigateFromAddressesUseCase: NavigateFromAddressesUseCase

) : ViewModel() {


    val addressesList = MutableLiveData<List<Address>>()

    val selectedAddress = MutableLiveData<Address>()

    val roomsList = MutableLiveData<List<Room>>()

    val devicesList = MutableLiveData<List<Device>>()


    fun init() {

        CoroutineScope(Dispatchers.IO).launch {

            val addressesListTmp = ArrayList<Address>()

            val keys = getAvailableAddressesKeysUseCase.execute()

            repeat(keys.size) {

                addressesListTmp.add(getAddressUseCase.execute(addressKey = keys[it]))

            }

            val roomsListTmp = getRoomListUseCase.execute()

            val devicesListTmp =
                getDevicesListInRoomUseCase.execute(roomId = ALLDEVICES_ROOM_ID.toLong())


            withContext(Dispatchers.Main) {

                addressesList.value = addressesListTmp
                roomsList.value = roomsListTmp
                devicesList.value = devicesListTmp

            }

        }

    }


    fun selectAddress(selectedAddressKey: String) {

        runBlocking {

            changeSelectedAddressUseCase.execute(selectedAddressKey)

            selectedAddress.value = getAddressUseCase.execute(addressKey = selectedAddressKey)

            roomsList.value = getRoomListUseCase.execute()

            devicesList.value =
                getDevicesListInRoomUseCase.execute(roomId = ALLDEVICES_ROOM_ID.toLong())
        }


    }

    fun getSelectedAddress() {

        CoroutineScope(Dispatchers.Main).launch {

            selectedAddress.value =
                getAddressUseCase.execute(addressKey = getSelectedAddressKeyUseCase.execute())

        }


    }


    fun deleteAddress(addressKey: String) {

        CoroutineScope(Dispatchers.IO).launch {

            removeAddressUseCase.execute(addressKey)

            init()

            getSelectedAddress()

        }

    }

    fun navigateToNewAddress() {

        navigateFromAddressesUseCase.toAddAddress()

    }

    fun back() {

        navigateFromAddressesUseCase.toDataLoading()

    }

}