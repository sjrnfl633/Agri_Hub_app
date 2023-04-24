package com.example.agri_hub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.agri_hub.data.ApiService
import com.example.agri_hub.data.User
import com.example.agri_hub.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    companion object {
        var accessToken: String = ""
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var nickname : String =""
        var phone_number: String=""
        var username: String=""
        UserApiClient.instance.me { user, error ->
            nickname = "${user?.kakaoAccount?.profile?.nickname}"
            phone_number = "${user?.kakaoAccount?.phoneNumber}"
            username = "${user?.kakaoAccount?.email}"
            Log.e("nick2", nickname)
            Log.e("phone2", phone_number)
            Log.e("email2", username)
            SignRequest(nickname = nickname, phone_number = phone_number, username = username)
        }

        // 카카오 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }


        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효 하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        binding.kakaoLoginButton.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.me { user, error ->
                     nickname = "${user?.kakaoAccount?.profile?.nickname}"
                     phone_number = "${user?.kakaoAccount?.phoneNumber}"
                     username = "${user?.kakaoAccount?.email}"
                    Log.e("nick3", nickname)
                    Log.e("phone3", phone_number)
                    Log.e("email3", username)
                    SignRequest(nickname, phone_number, username)
                }
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.me { user, error ->
                    nickname = "${user?.kakaoAccount?.profile?.nickname}"
                    phone_number = "${user?.kakaoAccount?.phoneNumber}"
                    username = "${user?.kakaoAccount?.email}"
                    Log.e("nic4", nickname)
                    Log.e("phone4", phone_number)
                    Log.e("email4", username)
                    SignRequest(nickname, phone_number, username)
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }
        }
    }

    private fun SignRequest(username: String, nickname: String, phone_number: String ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.247:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        Log.e("username333", username)
        Log.e("nickname333", nickname)
        Log.e("phone_numbe333r", phone_number)
        val user = User(
            username = username,
            nickname = nickname,
            phone_number = phone_number
        )

        Log.e("username444", "$user")
        val gson = Gson()
        val userJsonString = gson.toJson(user)
        Log.e("username555", "$userJsonString")
        //JsonString to JsonObject
        val userJsonObject = JSONObject(Gson().toJson(user))
        Log.e("username666", "$userJsonObject")
        apiService.createUser(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.e("인증", "test success")
                } else {
                    Log.e("인증", response.code().toString())
                    Log.e("인증", response.message())
                    Log.e("인증", response.errorBody().toString())
                    signinRequest(username, nickname, phone_number)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("인증", t.stackTraceToString().toString())
            }
        })
    }
    private fun signinRequest(username: String, nickname: String, phone_number: String ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.247:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        Log.e("username001", username)
        Log.e("nickname001", nickname)
        Log.e("phone_number001", phone_number)
        val user = User(
            username = username,
            nickname = nickname,
            phone_number = phone_number
        )

        Log.e("username444", "$user")
        apiService.loginUser(user).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    Log.e("인증2", "test success")
                    Log.e("인증2", response.headers().toString())
                    Log.e("인증2", response.body().toString())
                    val json = response.body().toString()
                    val jsonObject = JSONObject(json)
                    MainActivity.accessToken = jsonObject.getString("accessToken")


                } else {
                    Log.e("인증2", response.code().toString())
                    Log.e("인증2", response.message())
                    Log.e("인증2", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("인증", t.stackTraceToString().toString())
            }
        })
    }
}
