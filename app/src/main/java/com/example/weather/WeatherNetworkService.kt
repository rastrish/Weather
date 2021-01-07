package com.example.weather

import com.example.weather.model.TemperatureResponse
import retrofit2.http.*

interface WeatherNetworkService {

    @GET(EndPoints.TEMP)
    suspend fun getTemp(@Query ("lat") lat :Double, @Query("lon") lon : Double, @Query("appid") appId : String ): TemperatureResponse


}