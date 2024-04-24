package com.example.smartcontrollerv3.main.di

import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.okhttp.WifiHandler
import com.example.domain.domain.usecase.DeleteDeviceUseCase
import com.example.domain.domain.usecase.GetDeviceUseCase
import com.example.domain.domain.usecase.GetDevicesListInRoomUseCase
import com.example.domain.domain.usecase.SaveDeviceUseCase
import com.example.domain.domain.usecase.address.AddNewAddressUseCase
import com.example.domain.domain.usecase.address.GetAddressUseCase
import com.example.domain.domain.usecase.address.RemoveAddressUseCase
import com.example.domain.domain.usecase.data.ActualizeAddresses
import com.example.domain.domain.usecase.navigation.NavigateBackUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromADTRFPUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromADTRUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromAddressesUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromCFAUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromDataLoadingUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromHomeUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromLogIn
import com.example.domain.domain.usecase.navigation.NavigateFromLogUpUseCase
import com.example.domain.domain.usecase.navigation.NavigateFromWelcomePageUseCase
import com.example.domain.domain.usecase.navigation.NavigationControllerSetStartUseCase
import com.example.domain.domain.usecase.rooms.AddDeviceToRoomUseCase
import com.example.domain.domain.usecase.rooms.AddNewRoomUseCase
import com.example.domain.domain.usecase.rooms.DeleteRoomUseCase
import com.example.domain.domain.usecase.rooms.GetRoomListUseCase
import com.example.domain.domain.usecase.rooms.GetRoomUseCase
import com.example.domain.domain.usecase.rooms.RemoveDeviceFromRoomUseCase
import com.example.domain.domain.usecase.settings.ChangeSelectedAddressUseCase
import com.example.domain.domain.usecase.settings.GetSelectedAddressKeyUseCase
import com.example.domain.domain.usecase.user.GetAvailableAddressesKeysUseCase
import com.example.domain.domain.usecase.user.GetCurrentUserUseCase
import com.example.domain.domain.usecase.user.IsSignedInUseCase
import com.example.domain.domain.usecase.user.LogOutUseCase
import com.example.domain.domain.usecase.user.RegisterNewUserUseCase
import com.example.domain.domain.usecase.user.SignInUseCase
import com.example.domain.domain.usecase.wifi.WifiGetAndApplyUseCase
import com.example.domain.domain.usecase.wifi.WifiSendAndApplyUseCase
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.presentation.device.DeviceFragment
import org.koin.dsl.module

val domainModule = module {


    factory<GetDeviceUseCase> {
        GetDeviceUseCase(
           addressRepository = get()
        )
    }

    factory<SaveDeviceUseCase> {
        SaveDeviceUseCase(
            addressRepository = get()
        )
    }

    factory<GetDevicesListInRoomUseCase> {
        GetDevicesListInRoomUseCase(
            addressRepository = get()
        )
    }

    factory<DeleteDeviceUseCase> {
        DeleteDeviceUseCase(
            addressRepository = get()
        )
    }


    factory<NavigationControllerInterface> {
        get<NavigationController>()
    }

    factory<NavigateBackUseCase> {
        NavigateBackUseCase(
            navigationControllerInterface = get()
        )
    }



    //
    //
    //WIFI BLOCK
    //
    //




    single<WifiHandler> {
        WifiHandler(
            settingsRepository = get()
        )
    }

    factory<WifiGetAndApplyUseCase> {
        WifiGetAndApplyUseCase(
            addressRepository = get(),
            wifiHandler = get()
        )
    }

    factory<WifiSendAndApplyUseCase> {
        WifiSendAndApplyUseCase(
            addressRepository = get(),
            wifiHandler = get()
        )
    }


    //
    //
    //
    //
    //



    factory<GetRoomListUseCase> {
        GetRoomListUseCase(get())
    }

    factory<AddDeviceToRoomUseCase> {
        AddDeviceToRoomUseCase(
           addressRepository = get()
        )
    }

    factory<AddNewRoomUseCase> {
        AddNewRoomUseCase(
            addressRepository = get()
        )
    }

    factory<RemoveDeviceFromRoomUseCase> {
        RemoveDeviceFromRoomUseCase(
            addressRepository = get())
    }

    factory<DeleteRoomUseCase> {
        DeleteRoomUseCase(
            addressRepository = get())
    }

    factory<ChangeSelectedAddressUseCase> {
        ChangeSelectedAddressUseCase(
            settingsRepository =  get()
        )
    }

    factory<GetSelectedAddressKeyUseCase> {
        GetSelectedAddressKeyUseCase(
            settingsRepository = get()
        )
    }

    factory<GetAvailableAddressesKeysUseCase> {
        GetAvailableAddressesKeysUseCase(
            userRepository = get(),
            authRepository = get()
        )
    }

    factory<RemoveAddressUseCase> {
        RemoveAddressUseCase(
            addressRepository = get(),
            settingsRepository = get()
        )
    }

    factory<AddNewAddressUseCase> {
        AddNewAddressUseCase(
            addressRepository = get(),
            authRepository = get()
        )
    }

    factory<NavigateFromLogIn> {
        NavigateFromLogIn(
            navigationControllerInterface = get(),
            dataLoadingFragmentId = R.id.dataLoadingFragment
        )
    }

    factory<NavigateFromWelcomePageUseCase> {
        NavigateFromWelcomePageUseCase(
            navigationControllerInterface = get(),
            logInFragmentId = R.id.logInFragment,
            logUpFragmentId = R.id.logUpFragment
        )
    }

    factory<NavigationControllerSetStartUseCase> {
        NavigationControllerSetStartUseCase(
            navigationControllerInterface = get()
        )
    }

    factory<NavigateFromCFAUseCase> {
        NavigateFromCFAUseCase(
            navigationControllerInterface = get(),
            homeFragmentId = R.id.homeFragment
        )
    }

    factory<NavigateFromDataLoadingUseCase> {
        NavigateFromDataLoadingUseCase(
            navigationControllerInterface = get(),
            homeFragmentId = R.id.homeFragment,
            createFirstAddressFragmentId = R.id.createFirstAddressFragment
        )
    }

    factory<LogOutUseCase> {
        LogOutUseCase(
            authRepository = get(),
            navigationControllerInterface = get(),
            welcomePageFragmentId = R.id.welcomePageFragment
        )
    }

    factory<NavigateFromHomeUseCase> {
        NavigateFromHomeUseCase(
            navigationControllerInterface = get(),
            addressesFragmentId = R.id.addressesFragment,
            addRoomFragmentId = R.id.addRoomFragment,
            addDeviceToRoomFragmentId = R.id.addDeviceToRoomFirstPageFragment,
            deviceFragmentId = R.id.deviceFragment,
            keyDeviceId = DeviceFragment().KEY_DEVICE_ID,
            keyRoomPosition = DeviceFragment().KEY_ROOM_ID,
            profileFragmentId = R.id.profileFragment
        )
    }

    factory<NavigateFromLogUpUseCase> {
        NavigateFromLogUpUseCase(
            navigationControllerInterface = get(),
            dataLoadingFragmentId = R.id.dataLoadingFragment
        )
    }

    factory<NavigateFromADTRUseCase> {
        NavigateFromADTRUseCase(
            navigationControllerInterface = get(),
            homeFragmentId = R.id.homeFragment
        )
    }

    factory<NavigateFromADTRFPUseCase> {
        NavigateFromADTRFPUseCase(
            navigationControllerInterface = get(),
            addNewDeviceFragmentId = R.id.addNewDeviceFragment,
            addDeviceToRoomFragmentId = R.id.addDeviceToRoomFragment
        )
    }

    factory<RegisterNewUserUseCase> {
        RegisterNewUserUseCase(
            userRepository = get(),
           authRepository = get()
        )
    }

    factory<SignInUseCase> {
        SignInUseCase(
            authRepository = get(),
            userRepository = get(),
            settingsRepository = get()
        )
    }

    factory<IsSignedInUseCase> {
        IsSignedInUseCase(
            authRepository = get()
        )
    }

    factory<GetCurrentUserUseCase> {
        GetCurrentUserUseCase(
            authRepository = get(),
            userRepository = get()
        )
    }


    factory<ActualizeAddresses> {
        ActualizeAddresses(
            addressRepository = get(),
            userRepository = get(),
            settingsRepository = get(),
            authRepository = get(),

            )
    }

    factory<GetRoomUseCase> {
        GetRoomUseCase(
            addressRepository = get()
        )
    }

    factory<GetAddressUseCase> {
        GetAddressUseCase(
            addressRepository = get()
        )
    }


    factory<NavigateFromAddressesUseCase> {
        NavigateFromAddressesUseCase(
            navigationControllerInterface = get(),
            dataLoadingFragmentId = R.id.dataLoadingFragment,
            addAddressFragmentId = R.id.addAddressFragment
        )
    }

}