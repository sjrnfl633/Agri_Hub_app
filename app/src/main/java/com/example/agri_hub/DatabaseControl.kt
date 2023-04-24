//package com.example.agri_hub
//
//import android.util.Log
//import android.widget.ImageView
//import com.google.firebase.database.*
//
//class DatabaseControl(email:String) {
//    private var database = FirebaseDatabase.getInstance()
//    private lateinit var value:String
//    private lateinit var path:String
//    val ref : DatabaseReference = database.getReference("$path")
//
//    private fun setListener(ref : DatabaseReference, switchBtn: ImageView){
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val post = dataSnapshot.value
//                Log.i("firebase", "Got value2 $post")
//                ref.child("state").get().addOnSuccessListener {
//                    Log.i("firebase", "Got value ${it.value}")
//                    state = it.value as String
//                    initialized = true
//                    // 스위치 이미지 변경
//                    switchImageToggle(switchBtn)
//                }.addOnFailureListener{
//                    Log.e("firebase", "Error getting data", it)
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w("firebase", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        ref.addValueEventListener(postListener)
//    }
//
//}