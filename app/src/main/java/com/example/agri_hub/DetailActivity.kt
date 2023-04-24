package com.example.agri_hub

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import com.example.agri_hub.data.ApiService
import com.example.agri_hub.data.Condevice
import com.example.agri_hub.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        var deviceName = ""
        var umachineName =""
        var id = 0
        var actTime = 0
        var actTime2 = 0
        var actTime3 = 0
        var actTime4 = 0
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.actTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().startsWith("0")) {
                    // Remove the leading zero
                    binding.actTime.setText(s?.subSequence(1, s.length))
                    // Set the cursor to the end of the text
                    binding.actTime.setSelection(binding.actTime.text.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.actTime2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().startsWith("0")) {
                    // Remove the leading zero
                    binding.actTime2.setText(s?.subSequence(1, s.length))
                    // Set the cursor to the end of the text
                    binding.actTime2.setSelection(binding.actTime2.text.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.actTime3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().startsWith("0")) {
                    // Remove the leading zero
                    binding.actTime3.setText(s?.subSequence(1, s.length))
                    // Set the cursor to the end of the text
                    binding.actTime3.setSelection(binding.actTime3.text.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.actTime4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().startsWith("0")) {
                    // Remove the leading zero
                    binding.actTime4.setText(s?.subSequence(1, s.length))
                    // Set the cursor to the end of the text
                    binding.actTime4.setSelection(binding.actTime4.text.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }) // 앞에 0이 들어가는 것을 방지하기 위한 코드
        binding.statmessage.visibility = View.GONE
        binding.statmessage2.visibility = View.GONE
        binding.umachineStart.bringToFront()
        binding.umachineStart2.bringToFront()
        val intent = intent
        deviceName = intent.getStringExtra("umachine_id").toString()
        umachineName = intent.getStringExtra("umachine_add").toString()
        id = intent.getIntExtra("did", 0 )
        Log.e("deviceName", deviceName.toString())
        Log.e("umachineName", umachineName.toString())
        Log.e("did",id.toString())
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for Android 8.0 (API level 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("MyChannelId", "MyChannelName", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }


        binding.umachineStart.setOnClickListener {
            val rwtime1 = binding.actTime.text.toString()
            val time = rwtime1.toInt()
            // Convert the string to an integer value
            val rwtime2 = binding.actTime2.text.toString()
            val time2 = rwtime1.toInt()
            Log.e("time",time.toString())
            Log.e("time2",time2.toString())
            machineCon(deviceName,time,time2,0,0,0)
            binding.umachineStart.visibility = View.GONE
            binding.umachineStop.visibility = View.GONE
            binding.actTime.isEnabled = false
            binding.actTime2.isEnabled = false
            binding.umachineStart.isEnabled = false
            binding.umachineStop.isEnabled = false
            binding.statmessage2.text = "가동 준비 중..."
            binding.statmessage.visibility = View.VISIBLE

            val builder = NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.flower_bud)
                .setContentTitle("동작 알림")
                .setContentText("${umachineName} 기기 동작이 실행 되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Show the notification
            notificationManager.notify(0, builder.build())
            binding.umachineStatus.setImageResource(R.drawable.switch_on)
            Handler(Looper.getMainLooper()).postDelayed({
                // 실행 할 코드
                binding.statmessage.visibility = View.GONE
                binding.umachineStop.isEnabled = true
                binding.umachineStop.visibility = View.VISIBLE
            }, 4000)

        }

        binding.umachineStop.setOnClickListener {
            machineCon(deviceName,0,0,0,0,0)
            binding.umachineStart.visibility = View.GONE
            binding.umachineStop.visibility = View.GONE
            binding.actTime.isEnabled = true
            binding.actTime2.isEnabled = true
            binding.umachineStart.isEnabled = false
            binding.umachineStop.isEnabled = false
            binding.statmessage.text = "기기 정지 중..."
            binding.statmessage.visibility = View.VISIBLE

            val builder = NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.flower_bud)
                .setContentTitle("동작 알림")
                .setContentText("${umachineName} 기기 동작이 정지 되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Show the notification

            notificationManager.notify(1, builder.build())
            binding.umachineStatus.setImageResource(R.drawable.switch_off)
            Handler(Looper.getMainLooper()).postDelayed({
                // 실행 할 코드
                binding.statmessage.visibility = View.GONE
                binding.umachineStart.isEnabled = true
                binding.umachineStart.visibility = View.VISIBLE
            }, 4000)

        }

        binding.umachineStart2.setOnClickListener {
            binding.umachineStart2.visibility = View.GONE
            binding.umachineStop2.visibility = View.GONE
            binding.actTime3.isEnabled = false
            binding.actTime4.isEnabled = false
            binding.umachineStart2.isEnabled = false
            binding.umachineStop2.isEnabled = false
            binding.statmessage2.text = "가동 준비 중..."
            binding.statmessage2.visibility = View.VISIBLE

            val builder = NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.flower_bud)
                .setContentTitle("동작 알림")
                .setContentText("${umachineName} 기기 동작이 실행 되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Show the notification
            notificationManager.notify(2, builder.build())
            binding.umachineStatus2.setImageResource(R.drawable.switch_on)
            Handler(Looper.getMainLooper()).postDelayed({
                // 실행 할 코드
                binding.statmessage2.visibility = View.GONE
                binding.umachineStop2.isEnabled = true
                binding.umachineStop2.visibility = View.VISIBLE
            }, 4000)

        }
        binding.umachineStop2.setOnClickListener {
            binding.umachineStart2.visibility = View.GONE
            binding.umachineStop2.visibility = View.GONE
            binding.actTime3.isEnabled = true
            binding.actTime4.isEnabled = true
            binding.umachineStart2.isEnabled = false
            binding.umachineStop2.isEnabled = false
            binding.statmessage2.text = "가동 정지 중..."
            binding.statmessage2.visibility = View.VISIBLE

            val builder = NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.flower_bud)
                .setContentTitle("동작 알림")
                .setContentText("${umachineName} 기기 동작이 정지 되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Show the notification
            notificationManager.notify(3, builder.build())
            binding.umachineStatus2.setImageResource(R.drawable.switch_off)
            Handler(Looper.getMainLooper()).postDelayed({
                // 실행 할 코드
                binding.statmessage2.visibility = View.GONE
                binding.umachineStart2.isEnabled = true
                binding.umachineStart2.visibility = View.VISIBLE
            }, 4000)

        }

    }
    private fun machineCon(
        deviceName: String,
        rwtime1: Int,
        rwtime2: Int,
        rcval1: Int,
        rcval2: Int,
        rctime: Int
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.247:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val token = com.example.agri_hub.MainActivity.accessToken

        var device  = Condevice(
            device = deviceName!!,
            rwtime1 = rwtime1, rwtime2=rwtime2, rcval1=rcval1, rcval2=rcval2, rctime = rctime
        )
        Log.e("패스",id.toString())
        val call = apiService.manualControl(id = id,"Bearer $token",device)
        Log.e("사ㅣ발",id.toString())
        Log.e("사ㅣ발",token.toString())
        Log.e("사ㅣ발",deviceName.toString())
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    Log.e("조작3", response.body().toString())
                    Log.e("조작3", response.code().toString())
                    Log.e("조작3", "test success")
                } else {
                    Log.e("조작3", response.code().toString())
                    Log.e("조작3", response.message())
                    Log.e("조작3", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("조작3", t.stackTraceToString().toString())
            }
        })
    }
}
