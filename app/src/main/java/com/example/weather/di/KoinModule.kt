package com.example.weather.di

import com.example.weather.WeatherNetworkService
import com.example.weather.WeatherRepository
import com.example.weather.ui.MainActivityViewModel
import com.example.weather.Networking
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    fun provideMedtrailNetworkService(): WeatherNetworkService = Networking.createTrainManNetworkService()
    viewModel {
        MainActivityViewModel(get())
    }

    single {provideMedtrailNetworkService()}
    single { WeatherRepository(get()) }
    single { GifAdapter() }
}