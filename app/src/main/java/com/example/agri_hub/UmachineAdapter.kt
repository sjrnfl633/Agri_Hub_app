package com.example.agri_hub

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.agri_hub.umachine.UmachineItem



class UMachineAdapter(private val data: List<UmachineItem>, private val listener: (UmachineItem) -> Unit) :
    RecyclerView.Adapter<UMachineAdapter.ViewHolder>() {
    data class ItemData(
        var editTextValue: String = ""
    )

    var editTextValues = mutableListOf<String>()
    var temp  = mutableListOf<String>()
    private val itemList = mutableListOf<ItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_switch, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.actTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Save the EditText field value to the list
                editTextValues[holder.adapterPosition] = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val umachineName: TextView = itemView.findViewById(R.id.umachineName)
        private val umachineStart: Button = itemView.findViewById(R.id.umachine_start)
        private val umachineStatus: ImageView = itemView.findViewById(R.id.umachineStatus)
        val actTime : EditText = itemView.findViewById(R.id.act_time)



        init {
            for (i in data.indices) {
                editTextValues.add("")
            }
            actTime.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    editTextValues[adapterPosition] = s.toString()
                    Log.e("TAG", "onTextChanged: ${editTextValues[adapterPosition]}")
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        var isOn = false
        fun bind(item: UmachineItem) {
            // Obtain the NotificationManager system service
            val notificationManager = itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

// Create a notification channel for Android 8.0 (API level 26) and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("default", "Channel name", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }
// Set up the notification content and behavior
            val intent = Intent(itemView.context, MainActivity::class.java)

            umachineName.text = item.m_address
            umachineStart.setOnClickListener {
                val pendingIntent = PendingIntent.getActivity(itemView.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val notificationBuilder = NotificationCompat.Builder(itemView.context, "default")
                    .setSmallIcon(R.drawable.flower_bud)
                    .setContentTitle("동작 알림")
                    .setContentText("${item.m_address} 기기 동작이 ${editTextValues[adapterPosition]}분 실행 되었습니다.")
                    .setAutoCancel(true)
                    .setFullScreenIntent(pendingIntent, true)
                    .setContentIntent(pendingIntent)
                val notificationBuilder2 = NotificationCompat.Builder(itemView.context, "default")
                    .setSmallIcon(R.drawable.flower_bud)
                    .setContentTitle("동작 알림")
                    .setContentText("${item.m_address} 기기 동작이 정지 되었습니다.")
                    .setAutoCancel(true)
                    .setFullScreenIntent(pendingIntent, true)
                    .setContentIntent(pendingIntent)
                isOn = !isOn
                if (isOn) {
                    umachineStart.setBackgroundResource(R.drawable.stop_button)
                    umachineStatus.setImageResource(R.drawable.switch_on)
                    umachineStart.text = "중지"
                    notificationManager.notify(0, notificationBuilder.build())
                } else {
                    umachineStart.setBackgroundResource(R.drawable.start_button)
                    umachineStatus.setImageResource(R.drawable.switch_off)
                    umachineStart.text = "가동"
                    notificationManager.notify(0, notificationBuilder2.build())
                }
            }
        }
    }
}
