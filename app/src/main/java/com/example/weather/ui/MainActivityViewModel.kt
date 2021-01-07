package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weather.WeatherRepository
import com.example.weather.base.BaseViewModel
import com.example.weather.model.TemperatureResponse
import com.example.weather.utill.Resource
import com.example.weather.utill.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    val gif: MutableLiveData<Resource<TemperatureResponse>> = MutableLiveData()
    var list: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var latLon: MutableLiveData<Pair<Double, Double>> = MutableLiveData()


    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("Error", throwable?.message)
            gif.postValue(Resource(Status.ERROR, null))
        }

    fun getData() {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            weatherRepository.getTemp(latLon.value!!.first, latLon.value!!.second).let {
                Log.e("response", it.toString())
                gif.postValue(Resource(Status.SUCCESS, it))

            }
        }
    }


}

