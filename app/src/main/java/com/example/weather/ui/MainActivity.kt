package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.weather.R
import com.example.weather.base.BaseActivity
import com.example.weather.utill.LocationHelper
import com.example.weather.utill.PermissionUtils
import com.example.weather.utill.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityViewModel>(MainActivityViewModel::class) {

    private val strPermission = arrayOf(
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION"
    )

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {

        if (PermissionUtils.checkMultiplePermissionsGranted(this, strPermission)) {
            getLastLocation()
        } else {
            PermissionUtils.requestMultiplePermissions(this, strPermission)
        }

        viewModel.latLon.observe(this, Observer {
            viewModel.getData()
        })

    }

    override fun setupObservers() {
        super.setupObservers()


        viewModel.gif.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    textView.text = it.data?.name
                    val temp = it.data?.main?.temp?.minus(273.5)
                    textView2.text = temp.toString().substring(0, 2)
                    weather_desc.text = it.data!!.weather[0].description
                    high_temp.text = it.data.main.temp_max.minus(243.5).toString().substring(0, 2)
                    low_temp.text = it.data.main.temp_min.minus(243.5).toString().substring(0, 2)
                    wind_et.text = it.data.wind.speed.toString() + " km/h"
                    pressure.text = it.data.main.pressure.toString()
                    humidity_et.text = it.data.main.humidity.toString()


                }
            }
        })
    }

    private fun getLastLocation() {
        val locationHelper = LocationHelper(this)
        if (locationHelper.canGetLocation) {
            val latitude = locationHelper.getLatitude()
            val longitude = locationHelper.getLongitude()
            viewModel.latLon.postValue(Pair(latitude, longitude))
            Log.e("lat", latitude.toString())
            Log.e("lon", longitude.toString())
        } else {
            locationHelper.showSettingsAlert()
        }

    }

}