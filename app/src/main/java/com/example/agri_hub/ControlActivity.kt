package com.example.agri_hub

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agri_hub.data.ApiService
import com.example.agri_hub.data.MyData
import com.example.agri_hub.databinding.ActivityControlBinding
import com.example.agri_hub.umachine.UmachineItem
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlActivity : AppCompatActivity() {
    companion object {
        var json: String = ""
    }
    private lateinit var binding: ActivityControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding  = ActivityControlBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        machineRequest()
    }
    private fun machineRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.247:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val token = com.example.agri_hub.MainActivity.accessToken

        val call = apiService.machineNum("Bearer $token")
        call.enqueue(object : Callback<List<MyData>> {
            override fun onResponse(call: Call<List<MyData>>, response: Response<List<MyData>>) {
                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    Log.e("json",json)
                    val umachineList = Gson().fromJson(json, Array<UmachineItem>::class.java).toList()
                    //매뉴얼 db조회 - 토큰 존재
                    //
                    val adapter = DeviceAdapter(umachineList)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@ControlActivity)

                    Log.e("종류", response.body().toString())
                    Log.e("종류", response.code().toString())
                    Log.e("종류", "test success")
                } else {
                    Log.e("종류", response.code().toString())
                    Log.e("종류", response.message())
                    Log.e("종류", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<MyData>>, t: Throwable) {
                Log.e("종류", t.stackTraceToString().toString())
            }
        })
    }
}
//    private fun machineRequest() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.0.247:8000")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val apiService = retrofit.create(ApiService::class.java)
//        val token = com.example.agri_hub.MainActivity.accessToken
//
//        val call = apiService.machineNum("Bearer $token")
//        call.enqueue(object : Callback<List<MyData>> {
//            override fun onResponse(call: Call<List<MyData>>, response: Response<List<MyData>>) {
//                if (response.isSuccessful) {
//                    val jsonObject = Gson().toJson(response.body())
//                    Log.e("종류33", jsonObject.toString())
//                    json = response.body().toString()
//                    Log.e("종류", response.body().toString())
//                    Log.e("종류", response.code().toString())
//                    Log.e("종류", "test success")
//                } else {
//                    Log.e("종류", response.code().toString())
//                    Log.e("종류", response.message())
//                    Log.e("종류", response.errorBody().toString())
//                }//
//            }
//
//            override fun onFailure(call: Call<List<MyData>>, t: Throwable) {
//                Log.e("종류", t.stackTraceToString().toString())
//            }
//        })
//    }

//        navigation
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        pager
//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
//
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)