package com.example.domain.domain.usecase.data


import com.example.domain.domain.repository.AddressRepository
import com.example.domain.domain.repository.AuthRepository
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.repository.UserRepository

class ActualizeAddresses(
    private val addressRepository: AddressRepository,
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {

    suspend fun isEmptyAddresses(): Boolean {

        val userUid = authRepository.getCurrentUser().uid

        val user = userRepository.getUser(userUid)

        return user.addressesKeys.isEmpty()

    }


    suspend fun execute(): Boolean {

        val userUid = authRepository.getCurrentUser().uid

        settingsRepository.getAndApplyPosts()

        val addressesKeys = userRepository.getAvailableAddressesKeysFromNet(userUid)

        userRepository.setAvailableAddressesKeys(userUid = userUid,keys = addressesKeys)

        val curAddress = settingsRepository.getSelectedAddressKey()

        if(curAddress == ""){

            settingsRepository.changeSelectedAddressKey(selectedAddressKey = addressesKeys.first())

        }else{

            if(!addressesKeys.contains(curAddress)){

                settingsRepository.changeSelectedAddressKey(addressesKeys.first())

            }

        }

        return true

    }

//    fun getAndApply(
//        callback: OnCompleteCallback
//    ) {
//
//        var counter: Int = 0
//
//        firebaseDatabaseRepository.getAddressesList(
//            object : FirebaseDatabaseDataCallback {
//                override fun onDataGet(data: Any?) {
//
//                    applyAddressesData(data)
//
//                    counter++
//                    if (counter == 4) callback.onSuccess()
//                }
//
//            }
//        )
//
//        firebaseDatabaseRepository.getRoomsList(
//            object : FirebaseDatabaseDataCallback {
//                override fun onDataGet(data: Any?) {
//
//                    applyRoomsData(data)
//
//                    counter++
//                    if (counter == 4) callback.onSuccess()
//                }
//
//            }
//        )
//
//        firebaseDatabaseRepository.getDevicesList(
//            object : FirebaseDatabaseDataCallback {
//                override fun onDataGet(data: Any?) {
//
//                    applyDevicesData(data)
//
//                    counter++
//                    if (counter == 4) callback.onSuccess()
//                }
//
//            }
//        )
//
//        firebaseDatabaseRepository.getAndApplySettings(
//            object : OnCompleteCallback {
//                override fun onSuccess() {
//                    counter++
//                    if (counter == 4) callback.onSuccess()
//                }
//
//                override fun onFail() {
//                    TODO("Not yet implemented")
//                }
//            }
//        )
//
//    }
//
//
//    private fun applyDevicesData(data: Any?) {
//
//        if (data != null) {
//
//            if (data is ArrayList<*>) {
//
//                val idsList = ArrayList<Int>()
//                repeat(data.size -1 ){i ->
//                    idsList.add(i + 1)
//                }
//
//                idRepository.apply(idsList)
//
//                repeat(data.size - 1) { i ->
//
//                    val hash = data[i + 1] as HashMap<*, *>
//
//                    val device = FirebaseDevice(
//                        name = hash["name"] as String,
//                        id = (hash["id"] as Long).toInt(),
//                        ip = hash["ip"] as String,
//                        type = (hash["type"] as Long).toInt()
//                    )
//
//                    deviceRepository.apply(device)
//
//                }
//            } else if (data is HashMap<*, *>) {
//
//                val hash = data[data.keys.first()] as HashMap<*, *>
//
//                val device = FirebaseDevice(
//                    name = hash["name"] as String,
//                    id = (hash["id"] as Long).toInt(),
//                    ip = hash["ip"] as String,
//                    type = (hash["type"] as Long).toInt()
//                )
//
//                deviceRepository.apply(device)
//
//            }
//
//        }
//
//    }
//
//    private fun applyRoomsData(data: Any?) {
//
//        if (data != null) {
//
//            if (data is ArrayList<*>) {
//
//                repeat(data.size) { i ->
//
//                    val hash = data[i] as HashMap<*, *>
//
//                    val room = FirebaseRoom(
//                        name = hash["name"] as String,
//                        icon = (hash["icon"] as Long).toInt(),
//                        devicesIdsInRoom = if (hash["devicesIdsInRoom"] != null) hash["devicesIdsInRoom"] as ArrayList<Int> else ArrayList()
//                    )
//
//                    roomRepository.apply(room)
//
//                }
//            } else if (data is HashMap<*, *>) {
//
//                val hash = data[data.keys.first()] as HashMap<*, *>
//
//                val room = FirebaseRoom(
//                    name = hash["name"] as String,
//                    icon = (hash["icon"] as Long).toInt(),
//                    devicesIdsInRoom = if (hash["devicesIdsInRoom"] != null) hash["devicesIdsInRoom"] as ArrayList<Int> else ArrayList()
//                )
//
//                roomRepository.apply(room)
//
//            }
//
//        }
//
//    }
//
//    private fun applyAddressesData(data: Any?) {
//
//        if (data != null) {
//
//            if (data is ArrayList<*>) {
//
//                repeat(data.size) { i ->
//
//                    val hash = data[i] as HashMap<*, *>
//
//                    val address = FirebaseAddress(
//                        key = hash["key"] as String,
//                        name = hash["name"] as String,
//                        wifiSSID = hash["wifiSSID"] as String
//                    )
//
//                    addressRepository.apply(address)
//
//                }
//            } else if (data is HashMap<*, *>) {
//
//                val hash = data[data.keys.first()] as HashMap<*, *>
//
//                val address = FirebaseAddress(
//                    key = hash["key"] as String,
//                    name = hash["name"] as String,
//                    wifiSSID = hash["wifiSSID"] as String
//                )
//
//                addressRepository.apply(address)
//
//            }
//
//        }
//
//    }

}