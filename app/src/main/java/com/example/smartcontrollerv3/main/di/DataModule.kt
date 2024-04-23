package com.example.smartcontrollerv3.main.di

import com.example.data.data.repository.AddressRepositoryImplementation
import com.example.data.data.repository.DeviceRepositoryImplementation
import com.example.data.data.repository.IdRepositoryImplementation
import com.example.data.data.repository.RoomRepositoryImplementation
import com.example.data.data.repository.SettingsRepositoryImplementation
import com.example.data.data.repository.settingsStorage.SettingsStorageImpl
import com.example.data.data.repository.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.DeviceRepository
import com.example.domain.domain.repository.IdRepository
import com.example.domain.domain.repository.RoomRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.smartcontrollerv3.main.navigationController.NavigationController
import org.koin.dsl.module


val dataModule = module {

    single<DeviceRepository> {
        DeviceRepositoryImplementation(
            context = get(),
            settingsStorageInterface = get())
    }


    single<IdRepository> {
        IdRepositoryImplementation(
            context = get(),
            settingsStorageInterface = get())
    }


    single<RoomRepository> {
        RoomRepositoryImplementation(
            context = get(),
            settingsStorageInterface = get())
    }


    single<AddressRepository>{
        AddressRepositoryImplementation(
            context = get(),
            get())
    }

    single<SettingsRepository>{
        SettingsRepositoryImplementation(settingsStorageInterface = get())
    }

    single<SettingsStorageInterface>{
        SettingsStorageImpl(
            context = get()
        )
    }

}