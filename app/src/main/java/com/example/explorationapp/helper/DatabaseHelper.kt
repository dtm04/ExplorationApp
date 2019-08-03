package com.example.explorationapp.helper

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.explorationapp.room.User

private const val TAG = "Firebase Helper"
class DatabaseHelper constructor(userId: String) {
    private val database: DatabaseReference = FirebaseDatabase
        .getInstance()
        .reference
        .child(userId)

    init {
        database.onDisconnect().removeValue()
    }

    fun updateUserLoc(user: User) {
        /*
        val key = database.child(user.userId).push().key
        if(key == null) {
            Log.w(TAG, "Could not get push key for database!")
        }
        */
        // CREATE A HASHMAP AND UPDATE CHILDREN
        database.setValue(user)
        Log.e("Driver Info", " Updated")
    }

    fun deleteUser() {
        database.removeValue()
    }
}