package com.example.agri_hub

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException


class GrowActivity : AppCompatActivity() {
    private var webSocketClient: WebSocketClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri: URI
        try {
            uri = URI("ws://172.21.4.223:8002/grow")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }

        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake) {
                Log.d("WebSocket", "Connection opened")
            }

            override fun onMessage(message: String) {
                Log.d("WebSocket", "Received message: $message")
            }

            override fun onClose(code: Int, reason: String, remote: Boolean) {
                Log.d("WebSocket", "Connection closed")
            }

            override fun onError(ex: Exception) {
                Log.e("WebSocket", "Error occurred: ${ex.message}")
            }
        }
        webSocketClient?.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClient?.close()
    }
}