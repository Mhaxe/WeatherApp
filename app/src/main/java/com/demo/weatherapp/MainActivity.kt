package com.demo.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var cityNameText : TextView
    private lateinit var temperatureText : TextView
    private lateinit var humidityText : TextView
    private lateinit var descriptionText : TextView
    private lateinit var windText : TextView

    private lateinit var weatherIcon : ImageView
    private lateinit var refreshbutton: Button
    private lateinit var cityNameInput : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cityNameInput = findViewById(R.id.cityNameInput)
        cityNameText = findViewById(R.id.cityNameText)
        temperatureText = findViewById(R.id.temperatureText)
        humidityText = findViewById(R.id.humidityText)
        descriptionText = findViewById(R.id.descriptionText)
        windText = findViewById(R.id.windText)
        weatherIcon = findViewById(R.id.weatherIcon)
        refreshbutton =findViewById(R.id.fetchWeatherButton)
        
        FetchWeatherData("Mumbai")

        refreshbutton.setOnClickListener {
            var city = cityNameInput.text.toString()
            if (city.isNotEmpty()){
                FetchWeatherData(city)
            }else{
                Toast.makeText(this,"Enter a city name!",Toast.LENGTH_LONG).show()
                cityNameInput.setError("Enter a city name")

            }
        }
    }

    private fun FetchWeatherData(cityName: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY&units=metric"


        val executorService = Executors.newSingleThreadExecutor()

        executorService.execute {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response = client.newCall(request).execute()
                val result = response.body?.string() ?: ""

                runOnUiThread {
                    updateUI(result)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun updateUI(result: String) {
        if (result!=null){
            try {
                val jsonObject:JSONObject = JSONObject(result)
                val main = jsonObject.getJSONObject("main")
                val temperature = main.getDouble("temp")
                val humidity = main.getDouble("humidity")
                val wind = jsonObject.getJSONObject("wind")
                val windSpeed = wind.getDouble("speed")
                val cityName = jsonObject.getString("name")
                val weather = jsonObject.getJSONArray("weather")
                val description = weather.getJSONObject(0).getString("description")
                val iconCode = weather.getJSONObject(0).getString("icon")
                val resourceName = "ic_$iconCode"

                val resId = resources.getIdentifier(resourceName,"drawable",packageName)

                if(resId != 0){
                    weatherIcon.setImageResource(resId)
                }else{
                    weatherIcon.setImageResource(R.drawable.placeholder)
                }

                //set ui
                cityNameText.text = cityName
                temperatureText.text = temperature.toString()
                humidityText.text = humidity.toString()
                descriptionText.text = description.toString()
                windText.text =windSpeed.toString()




            }catch (e: JSONException){
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val API_KEY : String = "c0f1e470e3239866aa4f6430dc39ac48"
    }
}