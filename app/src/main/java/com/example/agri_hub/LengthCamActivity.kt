package com.example.agri_hub

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.bumptech.glide.Glide
import io.socket.client.IO
import io.socket.client.Socket
import com.example.agri_hub.databinding.ActivityLengthCamBinding
import io.socket.emitter.Emitter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LengthCamActivity : AppCompatActivity() {
    private lateinit var mSocket: Socket
    private lateinit var binding : ActivityLengthCamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLengthCamBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 별도의 스레드에서 네트워크 작업을 수행합니다.
        // 웹 페이지를 표시할 WebView를 생성합니다.
        val webView = WebView(this)

        // WebView의 설정을 구성합니다.
        webView.settings.apply {
            javaScriptEnabled = true // JavaScript를 사용할 수 있도록 설정합니다.
            allowContentAccess = true // WebView에서 컨텐츠 액세스를 허용합니다.
            allowFileAccess = true // WebView에서 파일 액세스를 허용합니다.
            domStorageEnabled = true // DOM 저장소를 사용할 수 있도록 설정합니다.
            useWideViewPort = true // 화면 비율을 맞추도록 설정합니다.
            loadWithOverviewMode = true // 페이지를 전체 화면으로 로드합니다.
        }

        // WebView에 URL을 로드합니다.
        webView.loadUrl("http://172.21.4.223:8001/size_feed")

        // WebView를 레이아웃에 추가합니다.
        binding.webViewContainer.addView(webView)
    }
}