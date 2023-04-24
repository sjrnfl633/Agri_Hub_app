package com.example.agri_hub

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agri_hub.umachine.UmachineItem

class SwitchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
//    private val data = listOf(
//        UmachineItem("test001", "1번 밭"),
//        UmachineItem("test002", "2번 밭"),
//        UmachineItem("test003", "3번 밭")
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_switch, container, false)
//        recyclerView = view.findViewById(R.id.recyclerView)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = UMachineAdapter(data) { item ->
//            // handle item click event here
//        }
//        return view
//    }
}