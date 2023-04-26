package com.example.agri_hub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agri_hub.databinding.ActivityCamMenuBinding

class CamMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCamMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCamMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageView5.setOnClickListener {
        val intent = Intent(this, JustCamActivity::class.java)
        startActivity(intent)
        }
        binding.imageView6.setOnClickListener {
            val intent = Intent(this, LengthCamActivity::class.java)
            startActivity(intent)
        }
    }
}