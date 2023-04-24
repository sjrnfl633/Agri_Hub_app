package com.example.agri_hub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.agri_hub.R
import com.google.firebase.database.*

class SettingFragment : Fragment() {
    private var database = FirebaseDatabase.getInstance()
    private var path = "User/a"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        val ref : DatabaseReference = database.getReference(path)

//        //리스너 등록
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val post = dataSnapshot.value
//                Log.i("firebase", "Got value2 $post")
//                ref.get().addOnSuccessListener {
//                    Log.i("firebase", "Got value ${it.value}")
//                    //state = it.value as String
//                    emailTextView.text = it.child("email").value as String
//                    wifiNameTextView.text = it.child("wifiName").value as String
//                    wifiPwTextView.text = it.child("wifiPw").value as String
//                    locationTextView.text = it.child("location").value as String
//                }.addOnFailureListener{
//                    Log.e("firebase", "Error getting data", it)
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w("firebase", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        ref.addValueEventListener(postListener)
//
////        howToUseTextView.setOnClickListener {
////            val dialog =
////        }
        return root
    }
}