package com.example.agri_hub

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.agri_hub.databinding.ActivityAgritabBinding
import okhttp3.*
import okio.ByteString
import org.json.JSONObject

class AgritabActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAgritabBinding
    private lateinit var imageView: ImageView
    private lateinit var webSocket: WebSocket
    private lateinit var recommendedJson: JSONObject
    var temperature:Double = 0.0
    var humidity:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAgritabBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        imageView = findViewById(R.id.imageView)

        startWebSocket()
    }

    private fun startWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://172.21.4.223:8002/grow").build()
        val listener = MyWebSocketListener()
        webSocket = client.newWebSocket(request, listener)
        Log.e("webSocket",webSocket.toString())
        Log.e("webSocket","connect")

    }

    private inner class MyWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            // WebSocket 연결이 열리면 호출되는 콜백 함수
            // 메시지를 전송하거나, 서버로부터 메시지를 수신할 수 있습니다.
            Log.e("webSocket","open")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.e("webSocket","text")
            // 서버로부터 텍스트 형태의 메시지를 수신하면 호출되는 콜백 함수
            val jsonObject = JSONObject(text)
            val imageBase64 = jsonObject.getString("image")
            val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            recommendedJson = jsonObject.getJSONObject("recommended")

            temperature = recommendedJson.getDouble("temperature")
            var temp = ""
            var temp2 = ""
            humidity = recommendedJson.getDouble("humidity")
            if (temperature.toString().length > 4) {
                temp = temperature.toString().substring(0, 4)
            } else {
                temp = temperature.toString()
            }
            if (humidity.toString().length > 4) {
                temp2 = humidity.toString().substring(0, 4)
            } else {
                temp2 = humidity.toString()
            }
            Log.e("온도",temperature.toString())
            Log.e("습도",humidity.toString())
            runOnUiThread {
                imageView.setImageBitmap(bitmap)
                binding.textView5.text = temp
                binding.textView6.text = temp2
            }
        }



        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            // 서버로부터 바이트 형태의 메시지를 수신하면 호출되는 콜백 함수
            Log.e("webSocket","message")
            val data = bytes.toByteArray()
            val json = String(data)
            val jsonObject = JSONObject(json)
            Log.e("webSocket",json)
            val imageBase64 = jsonObject.getString("image")
            val decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            runOnUiThread {
                imageView.setImageBitmap(bitmap)
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            // WebSocket 연결이 실패하면 호출되는 콜백 함수
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            // WebSocket 연결이 종료되면 호출되는 콜백 함수
        }
    }
}
