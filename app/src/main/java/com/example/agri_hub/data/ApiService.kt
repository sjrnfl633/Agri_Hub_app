package com.example.agri_hub.data

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/auth/signup")
    fun createUser(
        @Body user: User
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("/auth/signin")
    fun loginUser(
        @Body user: User
    ): Call<Any>

//    (GET)localhost:8000/irrigation/machine_stat == 기기 상태 확인
//    @Headers("Content-Type: application/json")


    @GET("/machine")
    fun machineNum(@Header("Authorization") accesstoken: String): Call<List<MyData>>

    @GET("/manual")
    fun manualGET(@Header("Authorization") accesstoken: String
    ): Call<Any>

    @POST("/manual")
    fun manualRequest(@Header("Authorization") accesstoken: String,
                      @Body device: Device
    ): Call<Any>
    @PATCH("/manual/{id}")
    fun manualControl(@Path("id") id: Int,
                      @Header("Authorization") accesstoken: String,
                      @Body device: Condevice
    ): Call<Any>

}
//interface ApiService {
//    @POST("/auth/signup")
//    fun createUser(@Body jsonObject: JSONObject): Call<Void>
//
//}

//{
//    {
//        "nickname":"이동호",
//        "phone_number":"+82 10-7122-3694",
//        "username":"skscscom@naver.com"
//    }
//}