package com.example.agri_hub.data

import retrofit2.http.GET

interface SensorDataService {
    @GET("/")
    suspend fun getData(): List<SensorData>
}