package com.example.agri_hub.climate

import com.google.gson.annotations.SerializedName

enum class Category {
    @SerializedName("POP")
    POP,
    @SerializedName("PTY")
    PTY,    // 강수형태
    @SerializedName("SKY")
    SKY,    // 하늘 상태
    @SerializedName("TMP")
    TMP,    // 1시간 기온
}