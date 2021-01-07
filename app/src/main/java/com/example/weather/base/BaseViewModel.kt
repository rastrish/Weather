package com.example.weather.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.utill.Resource
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel() , KoinComponent {

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()


    fun onCreate(){
        messageStringId.postValue(Resource.success())
    }

}