package com.example.smartcontrollerv3.main.di

import com.example.data.data.repository.firebase.FirebaseAuthRepository
import com.example.data.data.repository.firebase.FirebaseRepository
import com.example.firebase.FirebaseAuthImpl
import com.example.firebase.FirebaseImpl
import com.example.firebase.storage.addresses.FirebaseDatabaseAddressesStorage
import com.example.firebase.storage.addresses.FirebaseDatabaseAddressesStorageInterface
import com.example.firebase.storage.auth.FirebaseDatabaseAuthStorage
import com.example.firebase.storage.auth.FirebaseDatabaseAuthStorageInterface
import com.example.firebase.storage.devices.FirebaseDatabaseDevicesStorage
import com.example.firebase.storage.devices.FirebaseDatabaseDevicesStorageInterface
import com.example.firebase.storage.rooms.FirebaseDatabaseRoomsStorage
import com.example.firebase.storage.rooms.FirebaseDatabaseRoomsStorageInterface
import com.example.firebase.storage.settings.FirebaseDatabaseSettingsStorage
import com.example.firebase.storage.settings.FirebaseDatabaseSettingsStorageInterface
import com.example.firebase.storage.users.FirebaseDatabaseUsersStorage
import com.example.firebase.storage.users.FirebaseDatabaseUsersStorageInterface
import org.koin.dsl.module


val firebaseModule = module {

    single<FirebaseAuthRepository> {

        FirebaseAuthImpl(
            firebaseDatabaseAuthStorageInterface = get(),
            firebaseDatabaseUsersStorageInterface = get()
        )

    }

    single<FirebaseRepository>{
        FirebaseImpl(
            firebaseDatabaseUsersStorageInterface = get(),
            firebaseDatabaseAuthStorageInterface = get(),
            firebaseDatabaseAddressesStorageInterface = get(),
            firebaseDatabaseSettingsStorageInterface = get(),
            firebaseDatabaseRoomsStorageInterface = get(),
            firebaseDatabaseDevicesStorageInterface = get()
        )
    }


    single<FirebaseDatabaseAuthStorageInterface>{
        FirebaseDatabaseAuthStorage()
    }

    single<FirebaseDatabaseUsersStorageInterface>{
        FirebaseDatabaseUsersStorage()
    }

    single<FirebaseDatabaseAddressesStorageInterface>{
        FirebaseDatabaseAddressesStorage()
    }

    single<FirebaseDatabaseSettingsStorageInterface> {
        FirebaseDatabaseSettingsStorage()
    }

    single<FirebaseDatabaseRoomsStorageInterface>{
        FirebaseDatabaseRoomsStorage()
    }

    single<FirebaseDatabaseDevicesStorageInterface>{
        FirebaseDatabaseDevicesStorage()
    }

}