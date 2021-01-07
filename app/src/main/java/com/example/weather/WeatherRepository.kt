package com.example.weather

import com.example.weather.model.TemperatureResponse
import com.example.weather.utill.Constant
import org.koin.core.KoinComponent

class WeatherRepository(private val weatherNetworkService: WeatherNetworkService) : KoinComponent  {

    suspend fun getTemp(lat : Double ,lon : Double) : TemperatureResponse {
       return weatherNetworkService.getTemp(lat,lon,Constant.appId)
    }

}