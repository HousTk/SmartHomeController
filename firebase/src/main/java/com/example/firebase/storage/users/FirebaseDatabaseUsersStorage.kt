package com.example.firebase.storage.users

import com.example.data.data.repository.models.FirebaseUser
import com.example.firebase.utils.DATABASE_URL
import com.example.firebase.utils.USERS_KEY
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FirebaseDatabaseUsersStorage(): FirebaseDatabaseUsersStorageInterface {

    private val database =
        Firebase.database(DATABASE_URL)
            .reference
            .child(USERS_KEY)


    override suspend fun addUser(user: FirebaseUser) {

        database.child(user.uid).setValue(user)

    }

    override suspend fun getUser(userUid: String): Any? {

       return  database.child(userUid).get().await().value

    }

    override suspend fun addAvailableAddress(userUid: String, addressKey: String) {

        database.child(userUid).child("addressesKeys").get().addOnSuccessListener {
            val data = it.value

            val list = if(data != null){
                data as ArrayList<String>
            }else{
                ArrayList<String>()
            }

            list.add(addressKey)

            saveAddressesList(list, userUid)

        }


    }

    override suspend fun removeAddress(userUid: String, addressKey: String) {

        database.child(userUid).child("addressesKeys").get().addOnSuccessListener {
            val data = it.value

            val list = if(data != null){
                data as ArrayList<String>
            }else{
                ArrayList<String>()
            }

            list.remove(addressKey)

            saveAddressesList(list, userUid)

        }
    }

    private fun saveAddressesList(list:ArrayList<String>, userUid: String){
        database.child(userUid).child("addressesKeys").setValue(list)
    }
}