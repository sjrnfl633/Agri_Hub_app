package com.example.agri_hub.timer

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.agri_hub.R
import com.example.agri_hub.data.TimerItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.*

class TimerSettingDialog(context: Context) {
    private val dialog = Dialog(context)
    val context = context
    private lateinit var timePicker: TimePicker
    private lateinit var datedayTextView: TextView
    private lateinit var radioButtonOn: RadioButton
    private lateinit var radioButtonOff: RadioButton
    private lateinit var chips: List<Chip>
    lateinit var days: List<String>
    private var saveKey = ""
    private var mode = "create"
    private var path = "Timer/a"
    //private var dateStr = "null"

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(){
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_timer_setting)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        timePicker = dialog.findViewById(R.id.time)
        datedayTextView = dialog.findViewById(R.id.settingDateDayTextView)
        datedayTextView.text = LocalDate.now().plusDays(1).toString()
        radioButtonOn = dialog.findViewById(R.id.radioButtonOn)
        radioButtonOff = dialog.findViewById(R.id.radioButtonOff)
        radioButtonOn.isChecked = true
        days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        chips = listOf(dialog.findViewById(R.id.sunChip),
            dialog.findViewById(R.id.monChip), dialog.findViewById(R.id.tueChip),
            dialog.findViewById(R.id.wedChip), dialog.findViewById(R.id.thuChip),
            dialog.findViewById(R.id.friChip), dialog.findViewById(R.id.satChip))

        // 요일 버튼 눌렀을 때 화면에 해당 요일들을 띄움
        for(i in 0..6){
            chips[i].setOnCheckedChangeListener { _, _ ->
                var dayStr = ""
                for(j in 0..6)
                    if (chips[j].isChecked) dayStr += days[j] + " "

                Log.d("setting",dayStr)
                if(dayStr == "Sun Mon Tue Wed Thu Fri Sat ")
                    datedayTextView.text = "Everyday"
                else if(dayStr != "")
                    datedayTextView.text = dayStr
                else
                    datedayTextView.text = LocalDate.now().plusDays(1).toString()
            }
        }


        // 취소 버튼
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)
        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        // 저장 버튼
        val saveBtn = dialog.findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener{
            val data = getDialogData()
            val timerValues = data.toMap()
            val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference(path)

            if(mode == "create"){ // 새 타이머 추가
                saveKey = ref.push().key.toString()
                val childUpdates = hashMapOf<String, Any>(
                    "/$saveKey" to timerValues
                )
                ref.updateChildren(childUpdates)
            }
            else if(mode == "modify"){ // 기존 타이머 수정
                val childUpdates = hashMapOf<String, Any>(
                    "/$saveKey" to timerValues
                )
                ref.updateChildren(childUpdates)
            }

            dialog.dismiss()
            Toast.makeText(dialog.context, "Saved", Toast.LENGTH_SHORT).show()
        }

        // 날짜를 지정할 수 있는 달력 버튼
        val dateBtn = dialog.findViewById<ImageButton>(R.id.dateBtn)
        dateBtn.setOnClickListener{
            val dialog2 = TimerSettingDateDialog(context)
            dialog2.create(datedayTextView, chips)
            //dateStr = datedayTextView.text as String
            //Log.d("dateStr: ",dateStr)
        }
    }

    // 리스트에서 아이템을 클릭해 다이얼로그 띄웠을 경우 값 세팅
    @RequiresApi(Build.VERSION_CODES.O)
    fun setting(hour:Int, minute:Int, date:String, day:String, turningOn:Boolean, key:String){
        saveKey = key // 해당 아이템의 database 키 값
        mode = "modify" // 수정 모드로 전환
        create()

        timePicker.hour = hour
        timePicker.minute = minute
        if(date != "null") {
            datedayTextView.text = date
            //dateStr = date
        }
        else {
            var dayStr = ""
            if(day == "1111111") {
                dayStr = "Everyday"
                for (i in 0..6) chips[i].isChecked = true
            }
            else {
                for (i in 0..6) {
                    if (day[i] == '1') {
                        dayStr += days[i] + ", "
                        chips[i].isChecked = true
                    }
                }
            }
            datedayTextView.text = dayStr.substringBeforeLast(',')
        }
        if(turningOn) radioButtonOn.isChecked = true
        else radioButtonOff.isChecked = true

        showDialog()
    }

    // 현재 다이얼로그의 값을 TimerItem 형태로 반환
    private fun getDialogData(): TimerItem {
        var date = datedayTextView.text as String
        var day = "" //
        for(i in 0..6)
            if(chips[i].isChecked) {
                day += "1"
                date = "null"
            }
            else
                day += "0"

        Log.d("date :", date)

        val hour = timePicker.hour.toString()
        val minute = timePicker.minute.toString()
        val turningOn = radioButtonOn.isChecked

        return TimerItem("", date, day, hour, minute, "1", turningOn)
    }

    // 다이얼로그 띄우기
    fun showDialog(){
        dialog.show()
    }

    //할일
    // 키보드 내리기
}