package com.example.agri_hub.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import com.example.agri_hub.R
import com.example.agri_hub.data.TimerItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TimerRecyclerViewAdapter(private var timerList: ArrayList<TimerItem>) :
    RecyclerView.Adapter<TimerRecyclerViewAdapter.ViewHolder>() {
    //lateinit var context:Context
    // ViewHolder단위 객체로 View의 데이터 설정
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var timeTextView: TextView = view.findViewById(R.id.timeTextView)
        var dateDayTextView: TextView = view.findViewById(R.id.dateDayTextView)
        var stateSwitch: Switch = view.findViewById(R.id.onoffSwitch)
        var actionTextView: TextView = view.findViewById(R.id.actionTextView)
        var context: Context = view.context
        var days = listOf("일", "월", "화", "수", "목", "금", "토")
        var path = "Timer/a"
    }

    // 보여줄 아이템 개수만큼 View 생성
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_timer, viewGroup, false)

        return ViewHolder(view)
    }

    // 생성된 View에 보여줄 데이터 결정
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val hour = timerList[position].hour
        val minute = timerList[position].minute
        var time = ""
        //Log.d("viewholder: ", "$hour:${minute.toInt()}")
        time = if(hour.toInt() > 12)
            "${LocalDateTime.of(1, 1, 1, hour.toInt()-12, minute.toInt()).format(DateTimeFormatter.ofPattern("HH:mm"))} PM"
        else if (hour.toInt() == 12)
            "${LocalDateTime.of(1, 1, 1, hour.toInt(), minute.toInt()).format(DateTimeFormatter.ofPattern("HH:mm"))} PM"
        else
            "${LocalDateTime.of(1, 1, 1, hour.toInt(), minute.toInt()).format(DateTimeFormatter.ofPattern("HH:mm"))} AM"

        val day = timerList[position].day
        val date = timerList[position].date
        var dateDay = ""
        if (date != "null") dateDay = date
        else {
            if(day == "1111111") dateDay = "Everyday"
            else {
                var dayStr = ""
                for (i in 0..6)
                    if (day[i] == '1') dayStr += viewHolder.days[i] + ", "
                dateDay = dayStr.substringBeforeLast(",")
            }
        }
        var state = timerList[position].state == "1"
        val turningOn = timerList[position].turningOn
        val actionText =
            if (turningOn) viewHolder.context.getString(R.string.turningOn)
            else viewHolder.context.getString(R.string.turningOff)

        // on/off switch 클릭 (토글)
        viewHolder.stateSwitch.setOnClickListener {
            //viewHolder.stateSwitch.isChecked = !viewHolder.stateSwitch.isChecked
            val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference(viewHolder.path + "/" + timerList[position].key)
            if(state) { // 끄기
                ref.child("state").setValue("0")
                viewHolder.stateSwitch.isChecked = false
            }
            else { // 켜기
                ref.child("state").setValue("1")
                viewHolder.stateSwitch.isChecked = true

                val alarmManager = viewHolder.context.getSystemService(ALARM_SERVICE) as AlarmManager

                val intent = Intent(viewHolder.context, MyReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                        viewHolder.context, 1000, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT // 있으면 새것으로 업데이트
                )

                TODO("꺼짐, 켜짐 구분")
                TODO("요일 반복")
                TODO("타이머 간 구분")

                //val repeatInterval : Long = ALARM_TIMER * 1000L
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, hour.toInt()) // 시
                    set(Calendar.MINUTE, minute.toInt()) // 분
                }
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        //repeatInterval,
                        AlarmManager.INTERVAL_DAY, // 하루에 한 번씩 반복
                        pendingIntent)
            }
            state = !state
            Log.d("switch: ", state.toString())
        }

        // 아이템 클릭 - 수정 팝업
        viewHolder.itemView.setOnClickListener {
            val dialog = TimerSettingDialog(viewHolder.context)
            dialog.setting(hour.toInt(), minute.toInt(), date, day, turningOn, timerList[position].key)
        }
        // 아이템 롱클릭 - 삭제 팝업
        viewHolder.itemView.setOnLongClickListener(object:View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                //Toast.makeText(viewHolder.context, "롱클릭", Toast.LENGTH_SHORT).show()
                val dialog = TimerDeleteDialog(viewHolder.context)
                dialog.create(timerList[position].key)
                return true
            }
        })

        // 타이머 시간
        viewHolder.timeTextView.text = time
        // 타이머 날짜 또는 요일
        viewHolder.dateDayTextView.text = dateDay
        //타이머 on/off
        viewHolder.stateSwitch.isChecked = state
        // 끄는/ 켜는 동작
        viewHolder.actionTextView.text = actionText
    }

    // 보여줄 아이템이 몇 개인지
    override fun getItemCount() = timerList.size

    fun submitList(timerList: ArrayList<TimerItem>) {
        this.timerList = timerList
    }
}