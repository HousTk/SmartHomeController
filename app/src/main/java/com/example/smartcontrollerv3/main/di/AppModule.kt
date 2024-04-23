package com.example.smartcontrollerv3.main.di

import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.presentation.addAddress.AddAddressViewModel
import com.example.smartcontrollerv3.main.presentation.addDevice.AddDeviceViewModel
import com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.AddDeviceToRoomFirstPageViewModel
import com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.AddDeviceToRoomViewModel
import com.example.smartcontrollerv3.main.presentation.addRoom.AddRoomViewModel
import com.example.smartcontrollerv3.main.presentation.addresses.AddressesViewModel
import com.example.smartcontrollerv3.main.presentation.device.DeviceViewModel
import com.example.smartcontrollerv3.main.presentation.devices.DevicesViewModel
import com.example.smartcontrollerv3.main.presentation.home.HomeViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.CreateFirstAddressViewModel
import com.example.smartcontrollerv3.main.presentation.mainActivity.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NavigationController> {
        NavigationController(get())

    }

    viewModel<DevicesViewModel>{

        DevicesViewModel(
            get(),
            get(),
            get(),
            get()
        )

    }

    viewModel<MainViewModel> {

        MainViewModel(
        navigationController = get()
    )
    }

    viewModel<HomeViewModel>{
        HomeViewModel(
            navigationController = get(),
            getRoomListUseCase = get(),
            removeRoomUseCase =  get(),
            getDeviceUseCase =  get(),
            removeDeviceFromRoomUseCase =  get(),
            wifiJobList = get(),
            getDeviceTypeWifiUseCase = get(),
            sendTurnOffWifiUseCase = get(),
            sendTurnOnUseCase = get(),
            getDeviceSettingsWifiUseCase = get(),
            getDeviceStateWifiUseCase = get(),
            getCurrentAddressUseCase = get(),
            deleteDeviceUseCase = get()
        )
    }

    viewModel<AddDeviceViewModel>{
        AddDeviceViewModel(
            get(),
            get(),
            get(),
            addDeviceToRoomUseCase = get()
        )
    }

    viewModel<AddRoomViewModel> {
        AddRoomViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel<CreateFirstAddressViewModel>{
        CreateFirstAddressViewModel(
            get(),
            get(),
            get()

        )
    }

    viewModel<AddressesViewModel>{
        AddressesViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel<AddAddressViewModel>{
        AddAddressViewModel(
            navigationController = get(),
            addNewAddressUseCase = get()
        )
    }

    viewModel<DeviceViewModel>{
        DeviceViewModel(
            navigationController = get(),
            getDeviceUseCase = get(),
            deleteDeviceUseCase = get(),
            removeDeviceFromRoomUseCase = get()
        )
    }

    viewModel<AddDeviceToRoomFirstPageViewModel>{
        AddDeviceToRoomFirstPageViewModel(
            navigationController = get()
        )
    }

    viewModel<AddDeviceToRoomViewModel>{
        AddDeviceToRoomViewModel(
            navigationController = get()
        )
    }

}