package com.example.agri_hub.data

import com.google.firebase.database.Exclude
import java.io.Serializable

data class
TimerItem(
        var key: String,
        var date: String,
        var day: String,
        var hour: String,
        var minute: String,
        var state:String,
        var turningOn: Boolean // 불을 켜는 동작이면 1, 끄는 동작이면 0
): Serializable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "key" to key,
                "date" to date,
                "day" to day,
                "hour" to hour,
                "minute" to minute,
                "state" to state,
                "turningOn" to turningOn
        )
    }
}