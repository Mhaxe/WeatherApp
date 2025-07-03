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
        TODO("Not yet implemented")
        var url = "https:api.openweather.org/data/2.5/weather?q=" + cityName + "&appid=" + API_KEY + "&units=metric"

        var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    }

    companion object {
        private const val API_KEY : String = "c0f1e470e3239866aa4f6430dc39ac48"
    }
}