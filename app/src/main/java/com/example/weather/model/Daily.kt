package com.example.weather.model

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: FeelsLike,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<WeatherXX>,
    val wind_deg: Int,
    val wind_speed: Double
)