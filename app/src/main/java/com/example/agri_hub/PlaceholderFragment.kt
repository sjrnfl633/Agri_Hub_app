package com.example.agri_hub

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.agri_hub.R

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var pos = arguments?.getInt(ARG_SECTION_NUMBER)
        var root = inflater.inflate(R.layout.fragment_switch, container, false)
        when(pos){
            1-> root = inflater.inflate(R.layout.fragment_switch, container, false)
            2-> root = inflater.inflate(R.layout.fragment_timer, container, false)
            3-> root = inflater.inflate(R.layout.fragment_setting, container, false)
        }

//        val switchBtn = root.findViewById<ImageView>(R.id.imageView)
//        switchBtn.setOnClickListener {
//            //Toast.makeText(this.context, "ㅇㅇ", Toast.LENGTH_SHORT).show();
//            switchBtn.setImageResource(R.drawable.switch_on)
//        }

//        val textView: TextView = root.findViewById(R.id.section_label)
//        pageViewModel.text.observe(viewLifecycleOwner, Observer<String> {
//            textView.text = it
//        })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}