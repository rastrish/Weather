package com.example.weather.utill

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings;


class LocationHelper(context: Context) : LocationListener {
    lateinit var locationManager: LocationManager
    private  var mContext : Context = context
    private var location: Location? = null
    var isGPSEnabled = false
    private var latitude = 0.0
    private var longitude = 0.0
    private val strPermission = arrayOf(
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION"
    )


    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false


    init {
            getLocation()
    }


    @SuppressLint("MissingPermission")
     fun getLocation() : Location ?{

        try{
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isGPSEnabled && !isNetworkEnabled){

            }else{
                this.canGetLocation = true
                if(isNetworkEnabled){
                    if(PermissionUtils.checkMultiplePermissionsGranted(mContext as Activity,strPermission)){
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100L,0.0f,this)
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        location?.let { location ->
                            latitude = location.latitude
                            longitude = location.longitude
                        }

                    }
                    else{
                        PermissionUtils.requestMultiplePermissions(mContext as Activity,strPermission)
                    }
                }
                if(isGPSEnabled){
                    if (location == null){
                        if(PermissionUtils.checkMultiplePermissionsGranted(mContext as Activity,strPermission)){
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L,0.0f,this)
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            location?.let { location ->
                                latitude = location.latitude
                                longitude = location.longitude
                            }
                        }
                        else{
                            PermissionUtils.requestMultiplePermissions(mContext as Activity,strPermission)
                        }
                    }
                }

            }


        } catch (e:Exception){
            e.printStackTrace()
        }

        return location

    }

    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this)
        }
    }


    fun getLatitude() : Double{
        if (location != null){
            latitude = location!!.latitude
        }
        return latitude
    }

    fun getLongitude() : Double{
        if (location != null){
            latitude = location!!.longitude
        }
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }

    fun showSettingsAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle("Turn on location mode")

        // Setting Dialog Message
        alertDialog.setMessage("Zup needs your location permission to detect suspicious activity and keep your account safe")

        // On pressing Settings button
        alertDialog.setPositiveButton("Grant",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                mContext.startActivity(intent)
            })

        // on pressing cancel button
        alertDialog.show()
    }
    override fun onLocationChanged(location: Location?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }


}