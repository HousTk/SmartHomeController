package com.example.smartcontrollerv3.main.presentation.addresses

import androidx.lifecycle.ViewModel
import com.example.domain.domain.models.address.Address
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.models.room.Room
import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.usecase.GetDeviceListUseCase
import com.example.domain.domain.usecase.address.GetAddressesListUseCase
import com.example.domain.domain.usecase.address.RemoveAddressUseCase
import com.example.domain.domain.usecase.ids.GetIdsList
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.settings.ChangeSelectedAddressUseCase
import com.example.domain.domain.usecase.settings.GetSelectedAddressUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController

class AddressesViewModel(
    private val getAddressesListUseCase: GetAddressesListUseCase,
    private val getSelectedAddressUseCase: GetSelectedAddressUseCase,
    private val changeSelectedAddressUseCase: ChangeSelectedAddressUseCase,
    private val getRoomListUseCase: GetRoomListUseCase,
    private val getDevicesListUseCase: GetDeviceListUseCase,
    private val getIdsList: GetIdsList,
    private val navigationController:NavigationController,
    private val removeAddressUseCase: RemoveAddressUseCase
) : ViewModel() {


    fun selectAddress(selectedAddressPosition: Int) {
        changeSelectedAddressUseCase.execute(selectedAddressPosition)
    }

    fun getAddressesList(): ArrayList<Address> {

        return getAddressesListUseCase.execute()

    }

    fun getSelectedAddress(): Address {
        val listTMP = getAddressesList()

        return listTMP[getSelectedAddressPosition()]
    }

    fun getSelectedAddressPosition(): Int {

        return getSelectedAddressUseCase.execute()

    }

    fun deleteAddress(addressPosition:Int){
        removeAddressUseCase.execute(addressPosition)
    }

    fun navigateToNewAddress(){
        navigationController.navigateTo(R.id.addAddressFragment)
    }

    fun back(){
        navigationController.navigateBack()
    }

    fun getRoomList(): ArrayList<Room> {
        return getRoomListUseCase.execute()
    }

    fun getDevicesList(): ArrayList<Device> {
        return getDevicesListUseCase.execute(getIdsList.execute())
    }

}