package com.example.firebase.storage.devices


import com.example.data.data.repository.models.FirebaseDevice
import com.example.firebase.utils.ADDRESSES_KEY
import com.example.firebase.utils.DATABASE_URL
import com.example.firebase.utils.DEVICE_KEY
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await


class FirebaseDatabaseDevicesStorage(
) : FirebaseDatabaseDevicesStorageInterface {

    private val database =
        Firebase.database(DATABASE_URL)
            .reference
            .child(ADDRESSES_KEY)


    override suspend fun get(addressKey: String): Any? {

        return database.child(addressKey).child(DEVICE_KEY).get().await().value

    }

    override suspend fun saveDevice(device: FirebaseDevice, addressKey: String): Boolean {

        val id = device.id.toString()

        database.child(addressKey).child(DEVICE_KEY).child(id).setValue(device).await()

            return true

    }

    override suspend fun deleteDevice(deviceId: Long, addressKey: String): Boolean {

        database.child(addressKey).child(DEVICE_KEY).child(deviceId.toString()).setValue(null).await()

        return true

    }

//    fun setListener(listener: FirebaseDatabaseListener) {
//
//        val changeListener = object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val data = snapshot.value
//
//                if(data != null) {
//
//                    if(data is ArrayList<*>) {
//
//                        repeat(data.size - 1) { i ->
//
//                            val hash = data[i + 1] as HashMap<*, *>
//
//                            val device = Device(
//                                name = hash["name"] as String,
//                                id = (hash["id"] as Long).toInt(),
//                                ip = hash["ip"] as String,
//                                type = (hash["type"] as Long).toInt()
//                            )
//
//                            if (devicesStorageInterface.isDeviceExists(device.id)) {
//                                devicesStorageInterface.updateDevice(
//                                    device = device,
//                                    id = device.id
//                                )
//                            } else {
//                                devicesStorageInterface.addDevice(
//                                    SaveParamsDevice(
//                                        name = device.name,
//                                        ip = device.ip,
//                                        id = device.id
//                                    )
//                                )
//                            }
//
//                        }
//                    }else if(data is HashMap<*,*>){
//
//                        val hash = data[data.keys.first()] as HashMap<*,*>
//
//                        val device = Device(
//                            name = hash["name"] as String,
//                            id = (hash["id"] as Long).toInt(),
//                            ip = hash["ip"] as String,
//                            type = (hash["type"] as Long).toInt()
//                        )
//
//                        if (devicesStorageInterface.isDeviceExists(device.id)) {
//                            devicesStorageInterface.updateDevice(
//                                device = device,
//                                id = device.id
//                            )
//                        } else {
//                            devicesStorageInterface.addDevice(
//                                SaveParamsDevice(
//                                    name = device.name,
//                                    ip = device.ip,
//                                    id = device.id
//                                )
//                            )
//                        }
//
//                    }
//
//                    listener.onDataChange()
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//
//        database.addValueEventListener(changeListener)
//
//    }


}