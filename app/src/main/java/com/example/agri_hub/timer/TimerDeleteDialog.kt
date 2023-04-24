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

class TimerDeleteDialog(context: Context) {
    private val dialog = Dialog(context)
    val context = context
    private var path = "Timer/a"

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(key:String){
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_timer_delete)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        // 취소 버튼
        val cancelBtn = dialog.findViewById<Button>(R.id.deleteCancelBtn)
        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        // 삭제 버튼
        val deleteBtn = dialog.findViewById<Button>(R.id.deleteBtn)
        deleteBtn.setOnClickListener{
            val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference(path)
            ref.child(key).removeValue()

            dialog.dismiss()
            Toast.makeText(dialog.context, "Deleted", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }
}