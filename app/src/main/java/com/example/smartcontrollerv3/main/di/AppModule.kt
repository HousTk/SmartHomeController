package com.example.smartcontrollerv3.main.di

import com.example.smartcontrollerv3.main.navigationController.NavigationController
import com.example.smartcontrollerv3.main.presentation.addAddress.AddAddressViewModel
import com.example.smartcontrollerv3.main.presentation.addDevice.AddDeviceViewModel
import com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.AddDeviceToRoomFirstPageViewModel
import com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.addConnectedDevice.AddDeviceToRoomViewModel
import com.example.smartcontrollerv3.main.presentation.addRoom.AddRoomViewModel
import com.example.smartcontrollerv3.main.presentation.addresses.AddressesViewModel
import com.example.smartcontrollerv3.main.presentation.device.DeviceViewModel
import com.example.smartcontrollerv3.main.presentation.home.HomeViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.createFirstAddress.CreateFirstAddressViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.loadingPage.DataLoadingViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.logIn.LogInViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.logUp.LogUpViewModel
import com.example.smartcontrollerv3.main.presentation.logIn.welcomePage.WelcomePageViewModel
import com.example.smartcontrollerv3.main.presentation.mainActivity.MainViewModel
import com.example.smartcontrollerv3.main.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NavigationController> {
        NavigationController()
    }



    viewModel<MainViewModel> {

        MainViewModel(
            navigationController = get(),
            isSignedInUseCase = get()
        )
    }

    viewModel<HomeViewModel> {
        HomeViewModel(
            navigateFromHomeUseCase = get(),
            getRoomListUseCase = get(),
            deleteRoomUseCase = get(),
            getDeviceUseCase = get(),
            removeDeviceFromRoomUseCase = get(),
            deleteDeviceUseCase = get(),
            getRoomUseCase = get(),
            getAddressUseCase = get(),
            getSelectedAddressKeyUseCase = get(),
            wifiGetAndApplyUseCase = get(),
            wifiSendAndApplyUseCase = get(),
            getDevicesListInRoomUseCase = get(),
            liveDataRepository = get()
        )
    }

    viewModel<AddDeviceViewModel> {
        AddDeviceViewModel(
            saveDeviceUseCase = get(),
            get(),
            addDeviceToRoomUseCase = get()
        )
    }

    viewModel<AddRoomViewModel> {
        AddRoomViewModel(
            get(),
            addNewRoomUseCase = get(),
            getDevicesListInRoomUseCase = get(),
        )
    }

    viewModel<CreateFirstAddressViewModel> {
        CreateFirstAddressViewModel(
            addNewAddressUseCase = get(),
            navigateFromCFAUseCase = get()

        )
    }

    viewModel<AddressesViewModel> {
        AddressesViewModel(
            getAvailableAddressesKeysUseCase =  get(),
            getSelectedAddressKeyUseCase =  get(),
            changeSelectedAddressUseCase =  get(),
            getRoomListUseCase =  get(),
            removeAddressUseCase =  get(),
            getAddressUseCase = get(),
            getDevicesListInRoomUseCase = get(),
            navigateFromAddressesUseCase = get()

        )
    }

    viewModel<AddAddressViewModel> {
        AddAddressViewModel(
            navigationController = get(),
            addNewAddressUseCase = get()
        )
    }

    viewModel<DeviceViewModel> {
        DeviceViewModel(
            navigationController = get(),
            getDeviceUseCase = get(),
            deleteDeviceUseCase = get(),
            removeDeviceFromRoomUseCase = get(),
            wifiSendAndApplyUseCase = get(),
            liveDataRepository = get()
        )
    }

    viewModel<AddDeviceToRoomFirstPageViewModel> {
        AddDeviceToRoomFirstPageViewModel(
            navigateFromADTRFPUseCase = get()
        )
    }

    viewModel<AddDeviceToRoomViewModel> {
        AddDeviceToRoomViewModel(
            navigateFromADTRUseCase = get(),
            addDeviceToRoomUseCase = get(),
            getDevicesListInRoomUseCase = get()
        )
    }

    viewModel<WelcomePageViewModel> {
        WelcomePageViewModel(
            navigateFromWelcomePageUseCase = get()
        )
    }

    viewModel<LogInViewModel> {
        LogInViewModel(
            navigateFromLogIn = get(),
            signInUseCase = get()
        )
    }

    viewModel<LogUpViewModel> {
        LogUpViewModel(
            navigateFromLogUpUseCase = get(),
            registerNewUserUseCase = get()
        )
    }

    viewModel<ProfileViewModel>{
        ProfileViewModel(
            logOutUseCase = get()
        )
    }

    viewModel<DataLoadingViewModel>{
        DataLoadingViewModel(
            navigateFromDataLoadingUseCase = get(),
            actualizeAddresses = get()
        )
    }
}