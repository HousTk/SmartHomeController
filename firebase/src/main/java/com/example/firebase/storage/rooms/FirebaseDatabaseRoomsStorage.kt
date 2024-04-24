package com.example.firebase.storage.rooms

import com.example.data.data.repository.models.FirebaseRoom
import com.example.firebase.utils.ADDRESSES_KEY
import com.example.firebase.utils.DATABASE_URL
import com.example.firebase.utils.ROOMS_KEY
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await


class FirebaseDatabaseRoomsStorage(
): FirebaseDatabaseRoomsStorageInterface {

    private val database =
        Firebase.database(DATABASE_URL)
            .reference
            .child(ADDRESSES_KEY)




    override suspend fun getRoomList(addressKey: String): Any? {

        return database.child(addressKey).child(ROOMS_KEY).get().await().value

    }

    override suspend fun getRoom(roomId: Long, addressKey: String): Any? {

        return database.child(addressKey).child(ROOMS_KEY).child(roomId.toString()).get().await().value

    }

    override suspend fun saveRoom(room: FirebaseRoom, addressKey: String): Boolean {

        val id = room.id
        database.child(addressKey).child(ROOMS_KEY).child(id.toString()).setValue(room).await()

        return true

    }

    override suspend fun deleteRoom(roomId: Long, addressKey: String): Boolean {

        database.child(addressKey).child(ROOMS_KEY).child(roomId.toString()).setValue(null).await()

        return true
    }


//    override fun setListener(listener: FirebaseDatabaseListener) {
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
//                            val room  = Room(
//                                name = hash["name"] as String,
//                                icon = (hash["icon"] as Long).toInt(),
//                                devicesIdsInRoom = hash["deviceIdsInRoom"] as ArrayList<Int>
//                            )
//
//                            if (roomsStorageInterface.isRoomExists(i)) {
//                                roomsStorageInterface.updateRoom(roomPosition = i, room = room)
//                            } else {
//                                roomsStorageInterface.addToList(room)
//                            }
//
//                        }
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