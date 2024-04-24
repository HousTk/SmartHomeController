package com.example.firebase.storage.auth


import android.util.Log
import com.example.data.data.repository.models.FirebaseAuthUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

private const val TAG = "FirebaseDatabaseAuthStorage"
class FirebaseDatabaseAuthStorage(): FirebaseDatabaseAuthStorageInterface {

    private val auth: FirebaseAuth = Firebase.auth


    override fun isSignedIn(): Boolean {

        val currentUser = auth.currentUser

        return currentUser != null

    }

    override suspend fun createAccount(email:String, password:String): Boolean {

        val task = auth.createUserWithEmailAndPassword(email, password).await()

        return task.user != null


    }

    override suspend fun signIn(email: String, password: String): Boolean {

        val task = auth.signInWithEmailAndPassword(email, password).await()

        return task.user != null


    }

    override fun getUser(): FirebaseAuthUser {

        val fbUser = auth.currentUser

        if (fbUser != null) {

            return FirebaseAuthUser(
                email = fbUser.email ?: "none",
                uid = fbUser.uid
            )

        } else {

            Log.e(TAG, "User is not logged in.")

            throw Exception("User is not logged in.")

        }

    }


    override fun signOut() {
        auth.signOut()
    }
}