package com.example.smartcontrollerv3.main.di

import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceListUseCase
import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.navigation.NavigateBack
import com.example.domain.domain.usecase.SaveDeviceUseCase
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.domain.domain.usecase.address.GetAddressesListUseCase
import com.example.domain.domain.usecase.address.GetCurrentAddressUseCase
import com.example.domain.domain.usecase.address.RemoveAddressUseCase
import com.example.domain.domain.usecase.ids.AddNewIdUseCase
import com.example.domain.domain.usecase.ids.GetIdsList
import com.example.domain.domain.usecase.ids.GetNewId
import com.example.domain.domain.usecase.rooms.AddDeviceToRoomUseCase
import com.example.domain.domain.usecase.rooms.AddNewRoomUseCase
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase
import com.example.domain.domain.usecase.rooms.RemoveRoomUseCase
import com.example.domain.domain.usecase.settings.ChangeSelectedAddressUseCase
import com.example.domain.domain.usecase.settings.FirstStartCompleteUseCase
import com.example.domain.domain.usecase.settings.GetSelectedAddressUseCase
import com.example.domain.domain.usecase.settings.IsFirstStartUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceSettingsWifiUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceStateWifiUseCase
import com.example.domain.domain.usecase.wifi.GetDeviceTypeWifiUseCase
import com.example.domain.domain.usecase.wifi.SendDeviceSettingsWifiUseCase
import com.example.domain.domain.usecase.wifi.SendTurnOffWifiUseCase
import com.example.domain.domain.usecase.wifi.SendTurnOnWifiUseCase
import com.example.domain.domain.usecase.wifi.WifiJobList
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import org.koin.dsl.module

val domainModule = module {


    factory<GetDeviceUseCase> {
        GetDeviceUseCase(deviceRepository = get())
    }

    factory<SaveDeviceUseCase> {
        SaveDeviceUseCase(
            deviceRepository = get(),
            idRepository = get(),
            roomRepository = get())
    }

    factory<GetDeviceListUseCase> {
        GetDeviceListUseCase(deviceRepository = get())
    }

    factory<DeleteDeviceUseCase> {
        DeleteDeviceUseCase(
            deviceRepository = get(),
            idRepository =  get(),
            roomRepository = get()
            )
    }

    factory<GetIdsList> {
        GetIdsList(idRepository = get())
    }

    factory<GetNewId> {
        GetNewId(idRepository = get())
    }

    factory<AddNewIdUseCase> {
        AddNewIdUseCase(idRepository = get())
    }

    single<NavigationControllerInterface> {
        NavigationController(isFirstStartUseCase = get())
    }

    factory<NavigateBack> {
        NavigateBack(get())
    }


    single<WifiHandler> {
        WifiHandler()
    }

    single <WifiJobList>{
        WifiJobList()
    }

    factory<GetDeviceSettingsWifiUseCase> {
        GetDeviceSettingsWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<SendDeviceSettingsWifiUseCase> {
        SendDeviceSettingsWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<GetDeviceTypeWifiUseCase> {
        GetDeviceTypeWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<SendTurnOnWifiUseCase> {
        SendTurnOnWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<SendTurnOffWifiUseCase> {
        SendTurnOffWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<GetDeviceStateWifiUseCase> {
        GetDeviceStateWifiUseCase(
            wifiHandler = get(),
            deviceRepository = get(),
            wifiJobList = get()
        )
    }

    factory<GetRoomListUseCase> {
        GetRoomListUseCase(get())
    }

    factory<AddDeviceToRoomUseCase> {
        AddDeviceToRoomUseCase(get())
    }

    factory<AddNewRoomUseCase> {
        AddNewRoomUseCase(get())
    }

    factory<RemoveDeviceFromRoomUseCase> {
        RemoveDeviceFromRoomUseCase(get())
    }

    factory<RemoveRoomUseCase> {
        RemoveRoomUseCase(get())
    }

    factory<IsFirstStartUseCase> {
        IsFirstStartUseCase(
            settingsRepository = get()
        )
    }

    factory<FirstStartCompleteUseCase> {
        FirstStartCompleteUseCase(
            settingsRepository = get()
        )
    }

    factory<GetCurrentAddressUseCase> {
        GetCurrentAddressUseCase(
            addressRepository = get()
        )
    }

    factory<ChangeSelectedAddressUseCase> {
        ChangeSelectedAddressUseCase(
            get()
        )
    }

    factory<GetSelectedAddressUseCase> {
        GetSelectedAddressUseCase(
            get()
        )
    }

    factory<GetAddressesListUseCase> {
        GetAddressesListUseCase(
            get()
        )
    }

    factory<RemoveAddressUseCase> {
        RemoveAddressUseCase(
            get(),
            get()
        )
    }

    factory<AddNewAddressUseCase> {
        AddNewAddressUseCase(
            addressRepository = get(),
            roomRepository = get(),
            settingsRepository = get(),
            allDevicesRoomIcon = R.drawable.ic_room_alldevices_white
        )
    }
}