package com.example.domain.domain.repository

import com.example.domain.domain.models.address.Address

interface AddressRepository {

    fun addAddress(address: Address):Boolean

    fun removeAddress(addressPosition: Int):Boolean

    fun getAddress(addressPosition: Int):Address

    fun getCurrentAddress():Address

    fun getAddressList():ArrayList<Address>

}