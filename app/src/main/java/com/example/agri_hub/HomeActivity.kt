package com.example.agri_hub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agri_hub.databinding.ActivityHomeBinding
import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.agri_hub.climate.*
import com.example.agri_hub.data.ApiService
import com.example.agri_hub.data.MyData
import com.example.agri_hub.data.User
import com.example.agri_hub.databinding.ItemForecastBinding
import com.example.agri_hub.umachine.UmachineItem
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                updateLocation()
            } else -> {
            Toast.makeText(this,"위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName,null)
            }
            startActivity(intent)
            finish()
        }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))

        binding.agritab.setOnClickListener {
            val agritab = Intent(this, AgritabActivity::class.java)
            startActivity(agritab)
        }

        binding.controltab.setOnClickListener {
            val controltab = Intent(this, ControlActivity::class.java)
            startActivity(controltab)
        }

        binding.charttab.setOnClickListener {
            val charttab = Intent(this, ChartTabActivity::class.java)
            startActivity(charttab)
        }
        binding.cameratab.setOnClickListener {
            val intent = Intent(this, CamMenuActivity::class.java)
            startActivity(intent)
        }
    }








    private fun transformRainType(forecast: ForecastEntity) : String {
        return when(forecast.forecastValue.toInt()) {
            0 -> "없음"
            1->"비"
            2 -> "비/눈"
            3-> "눈"
            4-> "소나기"
            else-> ""
        }
    }
    private fun transformSky(forecast: ForecastEntity) : String {
        return when(forecast.forecastValue.toInt()) {
            1 -> "맑음"
            3 -> "구름 많음"
            4 -> "흐림"
            else-> ""
        }
    }

    private fun updateLocation() {
        val fusedLocationClient  = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            Thread{
                try {
                    val addressList = Geocoder(this, Locale.KOREA).getFromLocation(
                        it.latitude,
                        it.longitude,
                        1
                    )
                    runOnUiThread{
                        binding.locationTextView.text = addressList?.get(0)?.thoroughfare.orEmpty()
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()


//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://apis.data.go.kr/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            val service = retrofit.create(WeatherService::class.java)
//
//            val baseDateTime = BaseDateTime.getBaseDateTime()
//            val converter = GeoPointConverter()
//            val point = converter.convert(lat = it.latitude, lon = it.longitude)
//            service.getVillageForecast(
//                serviceKey = "I/tH7Id5tNl5EnG//AhdBTvEvqsJMDS/zTPm+fozvoZx8ddV07a7aNYqPYtdaN0JdO0dReaiV6ppgQsgHM/JzQ==",
//                baseDate = baseDateTime.baseDate,
//                baseTime = baseDateTime.baseTime,
//                nx = point.nx,
//                ny = point.ny
//            ).enqueue(object : Callback<WeatherEntity> {
//                override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
//                    val forecastDateTimeMap = mutableMapOf<String, Forecast>()
//                    val forecastList = response.body()?.response?.body?.items?.forecastEntities.orEmpty()
//                    for (forecast in forecastList) {
////                    Log.e("Forecast",forecast.toString())
//
//                        if(forecastDateTimeMap["${forecast.forecastDate}/${forecast.forecastTime}"] ==null) {
//                            forecastDateTimeMap["${forecast.forecastDate}/${forecast.forecastTime}"] =
//                                Forecast(
//                                    forecastDate = forecast.forecastDate,
//                                    forecastTime = forecast.forecastTime
//                                )
//                        }
//                        forecastDateTimeMap["${forecast.forecastDate}/${forecast.forecastTime}"]?.apply {
//                            when (forecast.category) {
//                                Category.POP-> precipitation = forecast.forecastValue.toInt()
//                                Category.PTY-> precipitationType = transformRainType(forecast)
//                                Category.SKY-> sky = transformSky(forecast)
//                                Category.TMP-> temperature = forecast.forecastValue.toDouble()
//                                else -> {}
//                            }
//                        }
//                    }
//
//                    val list = forecastDateTimeMap.values.toMutableList()
//                    list.sortWith { f1,f2 ->
//                        val f1DateTime = "${f1.forecastDate}${f1.forecastTime}"
//                        val f2DateTime = "${f2.forecastDate}${f2.forecastTime}"
//
//                        return@sortWith f1DateTime.compareTo(f2DateTime)
//                    }
//
//                    val currentForecast = list.first()
//                    binding.temperatureTextView.text = getString(R.string.temperature_text, currentForecast.temperature)
//                    binding.skyTextView.text = currentForecast.weather
//                    binding.precipitationTextView.text = getString(R.string.precipitation_text, currentForecast.precipitation)
//
//                    binding.childForecastLayout.apply {
//                        list.forEachIndexed { index, forecast ->
//                            if (index ==  0) { return@forEachIndexed }
//
//                            val itemView = ItemForecastBinding.inflate(layoutInflater)
//                            itemView.timeTextView.text = forecast.forecastTime
//                            itemView.weatherTextView.text = forecast.weather
//                            itemView.temperatureTextView.text = getString(R.string.temperature_text,forecast.temperature)
//                            addView(itemView.root)
//                        }
//                    }
//
//                    Log.e("Forecast", forecastDateTimeMap.toString())
//                }
//
//                override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
//                    t.printStackTrace()
//                }
//            })
        }
    }
}