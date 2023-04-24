package com.example.agri_hub.timer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
import com.example.agri_hub.R
import com.example.agri_hub.data.TimerItem
import com.example.agri_hub.timer.TimerRecyclerViewAdapter
import com.example.agri_hub.timer.TimerSettingDialog
import com.google.firebase.database.*

class TimerFragment : Fragment() {
    private var database = FirebaseDatabase.getInstance()
    private var value = ""
    private var path = "Timer/a"
    private var timerList = ArrayList<TimerItem>()
    private lateinit var timerRecyclerViewAdapter: TimerRecyclerViewAdapter
    private lateinit var ref : DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_timer, container, false)
        ref = database.getReference(path)
        timerRecyclerViewAdapter = TimerRecyclerViewAdapter(timerList)

        // 알람 추가 버튼
        val addBtn = root.findViewById<ImageButton>(R.id.addBtn)
        addBtn.setOnClickListener {
            val dialog = TimerSettingDialog(root.context)
            dialog.create()

            dialog.showDialog()
        }

//        root.findViewById<layout>()
        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        requireView().findViewById<RecyclerView>(R.id.timerRecyclerView).layoutManager =
//            GridLayoutManager(
//                requireContext(),
//                1,
//                GridLayoutManager.VERTICAL,
//                false
//            )
//        requireView().findViewById<RecyclerView>(R.id.timerRecyclerView).adapter = this.timerRecyclerViewAdapter
//
////        requireView().findViewById<RecyclerView>(R.id.timerRecyclerView).addItemDecoration(
////            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
////        ) // 구분선 넣기
//
//        timerRecyclerViewAdapter.submitList(timerList)
//
//
//        setListener(ref)
//
//        //timerRecyclerViewAdapter.setOnItemClickListener()
//    }

//    private fun setListener(ref : DatabaseReference){
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                //val post = dataSnapshot.value
//                //Log.i("firebase", "Got value2 $post")
//                Log.i("firebase", "Got value2 ${dataSnapshot.value}")
//
//                //리스트 초기화
//                timerList.clear()
//
//                if(dataSnapshot.hasChildren()){
//                    view?.findViewById<View>(R.id.no_timers_layout)?.visibility = View.GONE
//                    //자식들을 하나씩 리스트에 넣음
//                    for (chi in dataSnapshot.children){
//                        Log.i("firebase", "child ${chi.key}: $chi")
//                        timerList.add(TimerItem(
//                            chi.key as String,
//                            chi.child("date").value as String,
//                            chi.child("day").value as String,
//                            chi.child("hour").value as String,
//                            chi.child("minute").value as String,
//                            chi.child("state").value as String,
//                            chi.child("turningOn").value as Boolean))
//                    }
//                    Log.i("firebase", "arr: $timerList")
//                    timerRecyclerViewAdapter.submitList(timerList)
//                    timerRecyclerViewAdapter.notifyDataSetChanged()
//                }
//                else{
//                    view?.findViewById<View>(R.id.no_timers_layout)?.visibility = View.VISIBLE
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w("firebase", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        ref.addValueEventListener(postListener)
//    }

//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        Log.d("TouchEventTest", "called dispatchTouchEvent() in CustomView")
//        return super.dispatchTouchEvent(ev)
//    }
}