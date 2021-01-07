package com.example.weather

import com.example.weather.model.TemperatureResponse
import org.koin.core.KoinComponent

class WeatherRepository(private val weatherNetworkService: WeatherNetworkService) : KoinComponent  {

    suspend fun getTemp(lat : Double ,lon : Double) : TemperatureResponse {
       return weatherNetworkService.getTemp(lat,lon,"ee41ae7d84cef2f7f8a72b9e592d1d49")
    }

}