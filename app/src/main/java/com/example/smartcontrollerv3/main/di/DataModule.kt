package com.example.smartcontrollerv3.main.di

import com.example.data.data.repository.AddressRepositoryImpl
import com.example.data.data.repository.AuthRepositoryImpl
import com.example.data.data.repository.SettingsRepositoryImplementation
import com.example.data.data.repository.UserRepositoryImpl
import com.example.data.data.repository.livedataTest.LiveDataRepository
import com.example.data.data.repository.room.CacheStorageImpl
import com.example.data.data.repository.room.CacheStorageInterface
import com.example.data.data.repository.sharedPrefs.settingsStorage.SettingsStorageImpl
import com.example.data.data.repository.sharedPrefs.settingsStorage.SettingsStorageInterface
import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.repository.UserRepository
import com.example.smartcontrollerv3.R
import org.koin.dsl.module


val dataModule = module {

    // REPOSITORIES

    single<AddressRepositoryImpl>{
        AddressRepositoryImpl(
            allDevicesRoomIcon = R.drawable.ic_room_alldevices_white,
            firebaseRepository = get(),
            firebaseAuthRepository = get(),
            cacheStorageInterface = get(),
            settingsStorageInterface = get()
        )
    }

    single<SettingsRepository>{
        SettingsRepositoryImplementation(settingsStorageInterface = get())
    }

    single<AddressRepository> {
        get<AddressRepositoryImpl>()
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuthRepository = get(),
            firebaseRepository = get()
        )
    }

    single<UserRepository>{
        UserRepositoryImpl(
            cacheStorageInterface = get(),
            firebaseRepository = get()
        )
    }

    single<LiveDataRepository> {
        get<AddressRepositoryImpl>()
    }



    // STORAGES



    single<SettingsStorageInterface>{
        SettingsStorageImpl(
            context = get()
        )
    }


    // CACHE

    single<CacheStorageInterface>{
        CacheStorageImpl(
            context = get()
        )
    }




}