package com.example.smartcontrollerv3.main.presentation.addresses

interface AddressAdapterInterface {
    fun onAddressSelect(addressKey: String)
    fun addressDelete(addressKey: String)
}