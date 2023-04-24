package com.example.agri_hub.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyReceiver : BroadcastReceiver() {
    private var database = FirebaseDatabase.getInstance()
    private var path = "Switch/a"

    override fun onReceive(context: Context, intent: Intent) {
        val ref : DatabaseReference = database.getReference(path)

        val state = "1"

        // 수정하기
        ref.child("state").setValue(state)

        // 한 번 읽기
        ref.child("state").get().addOnSuccessListener {
            Log.i("firebaseStart", "Got value ${it.value}")
//            state = it.value as String
//            initialized = true
//            switchImageToggle(switchBtn)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}