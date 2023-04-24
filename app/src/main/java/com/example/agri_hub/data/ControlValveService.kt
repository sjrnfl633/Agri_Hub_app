package com.example.agri_hub.data

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ControlValveApiService {
    @PUT("control-valve")
    fun sendControlValveData(@Body data: JsonObject): Call<Unit>
}
