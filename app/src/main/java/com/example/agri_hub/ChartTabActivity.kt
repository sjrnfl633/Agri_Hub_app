package com.example.agri_hub

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.agri_hub.data.IrrigationData
import com.example.agri_hub.data.SensorData
import com.example.agri_hub.databinding.ActivityChartTabBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import org.json.JSONArray


class ChartTabActivity: AppCompatActivity() {
    private val binding by lazy{
        ActivityChartTabBinding.inflate(layoutInflater)
    }

    private fun touchButton(touchedButton: Button,touchedButtonBackground: GradientDrawable, button: GradientDrawable){
        touchedButtonBackground.setColor(Color.parseColor("#686D76"))
        button.setColor(Color.parseColor("#EEEEEE"))
        touchedButton.bringToFront()
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gson = Gson()
        val barChart = binding.barChart
        val irrijson =
            "[{\"id\":1,\"irrigation\":70,\"created_at\":\"04-20\"},{\"id\":2,\"irrigation\":10,\"created_at\":\"04-21\"},{\"id\":3,\"irrigation\":110,\"created_at\":\"04-22\"},{\"id\":4,\"irrigation\":60,\"created_at\":\"04-23\"},{\"id\":5,\"irrigation\":30,\"created_at\":\"04-24\"},{\"id\":6,\"irrigation\":180,\"created_at\":\"04-25\"},{\"id\":7,\"irrigation\":200,\"created_at\":\"04-26\"}]"
        val irrigationData = Gson().fromJson(irrijson, Array<IrrigationData>::class.java).toList()
        val entries = irrigationData.map { BarEntry(it.id.toFloat(), it.irrigation.toFloat()) }
        val dataSet = BarDataSet(entries, "Irrigation")
        dataSet.color = Color.BLUE
        val irridata = BarData(dataSet)
        barChart.data = irridata
        barChart.xAxis.valueFormatter =
            IndexAxisValueFormatter(irrigationData.map { it.created_at }.toTypedArray())
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.animateY(1000)


        val json = """[
   {
      "id":1,
      "temperature":10.2,
      "humidity":36.0,
      "created_at":"04-20"
   },
   {
      "id":2,
      "temperature":11.0,
      "humidity":47.6,
      "created_at":"04-21"
   },
   {
      "id":3,
      "temperature":11.3,
      "humidity":42.8,
      "created_at":"04-22"
   },
   {
      "id":4,
      "temperature":13.0,
      "humidity":63.6,
      "created_at":"04-23"
   },
   {
      "id":5,
      "temperature":9.0,
      "humidity":47.2,
      "created_at":"04-24"
   },
   {
      "id":6,
      "temperature":16.0,
      "humidity":38.7,
      "created_at":"04-25"
   },
   {
      "id":7,
      "temperature":18.0,
      "humidity":47.3,
      "created_at":"04-26"
   }
]"""

        val sensorDataList = gson.fromJson(
            json,
            Array<SensorData>::class.java
        )
        val temperatureEntries = sensorDataList.mapIndexed { index, data ->
            Entry(index.toFloat(), data.temperature.toFloat())
        }

        val temperatureDataSet = LineDataSet(temperatureEntries, "Temperature")
        temperatureDataSet.color = Color.RED
        temperatureDataSet.setDrawValues(false)

        val humidityEntries = sensorDataList.mapIndexed { index, data ->
            Entry(index.toFloat(), data.humidity.toFloat())
        }

        val humidityDataSet = LineDataSet(humidityEntries, "Humidity")
        humidityDataSet.color = Color.BLUE
        humidityDataSet.setDrawValues(false)

        val lineData = LineData(temperatureDataSet, humidityDataSet)

        val chart = binding.chart
        val xAxis = chart.xAxis

        chart.apply {
            data = CombinedData().apply {
                setData(lineData)
            }

            xAxis.valueFormatter = IndexAxisValueFormatter(sensorDataList.map { it.created_at })
            xAxis.labelCount = 7
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(true)

            axisRight.isEnabled = false

            val leftAxis = axisLeft

            leftAxis.setDrawGridLines(false)

            legend.apply {
                setDrawInside(false)
                form = Legend.LegendForm.CIRCLE
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                orientation = Legend.LegendOrientation.HORIZONTAL
                isWordWrapEnabled = true
            }

            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            animateY(1000, Easing.EaseInOutQuad)
        }
    }
}
