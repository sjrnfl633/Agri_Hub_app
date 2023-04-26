package com.example.agri_hub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agri_hub.data.ApiService
import com.example.agri_hub.data.Device
import com.example.agri_hub.databinding.ItemDeviceBinding
import com.example.agri_hub.umachine.UmachineItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DeviceAdapter(private val devices: List<UmachineItem>) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    var id: Int = 0
    lateinit var context: Context
    lateinit var detailIntent: Intent

    class ViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]
        context = holder.itemView.context
        detailIntent = Intent(context, DetailActivity::class.java)


        fun manualRequest() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.21.4.78:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(ApiService::class.java)
            val token = com.example.agri_hub.MainActivity.accessToken
            val deviceId = Device(device.device)
            val call = apiService.manualRequest(accesstoken = "Bearer $token", device = deviceId)
            Log.e("딩바이스", device.device)
            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Log.e("전송", response.body().toString())
                        Log.e("전송", response.code().toString())
                        Log.e("전송", "test success")
                        val intent = (context as Activity).intent
                        (context as Activity).finish() //현재 액티비티 종료 실시
                        (context as Activity).overridePendingTransition(0, 0) //효과 없애기
                        (context as Activity).startActivity(intent) //현재 액티비티 재실행 실시
                    } else {
                        Log.e("전송", response.code().toString())
                        Log.e("전송", response.message())
                        Log.e("전송", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("전송", t.stackTraceToString())
                }
            })
        }
        fun manualtrol(responseData: List<Map<String, Any>>, deviceName: String) {
            for (data in responseData) {
                if (data["device"] == deviceName) {
                    id = data["id"].toString().toDouble().toInt()
                    Log.e("전송", "Device ID $id found")
                    detailIntent.putExtra("did",id)
                    detailIntent.putExtra("umachine_id", deviceName)
                    detailIntent.putExtra("umachine_add", device.m_address)
                    Log.e("Intent",id.toString())
                    Log.e("Intent",deviceName)
                    Log.e("Intent",device.m_address.toString())
                    context.startActivity(detailIntent)
                    return
                }
            }
            manualRequest()
        }
        fun manuallist(position: Int) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.21.4.78:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(ApiService::class.java)
            val token = com.example.agri_hub.MainActivity.accessToken
            val deviceId = Device(device.device)
            val call = apiService.manualGET( accesstoken = "Bearer $token")
            Log.e("딩바이스11", device.device)
            call.enqueue(object : Callback<Any> {
                // callback methods here
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Log.e("전송100", response.body().toString())
                        Log.e("전송100", response.code().toString())
                        Log.e("전송100", "test success")

                        val responseData = response.body() as List<Map<String, Any>>
                        Log.e("전송100", responseData.toString())
                        manualtrol(responseData, device.device)
                    } else {
                        Log.e("전송", response.code().toString())
                        Log.e("전송", response.message())
                        Log.e("전송", response.errorBody().toString())
                        manualRequest()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("전송", t.stackTraceToString())
                    manualRequest()
                }
            })
        }









        holder.binding.deviceName.text = device.m_address
        holder.binding.deviceStatus.text = device.device

        holder.itemView.setOnClickListener {1
            manuallist(position)
        }
    }
    override fun getItemCount() = devices.size

}
