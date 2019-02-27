package com.klarna.challenge.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.klarna.challenge.util.PermissionHelper
import com.klarna.challenge.R
import com.klarna.challenge.model.data.Weather
import com.klarna.challenge.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){

    private var locationManager : LocationManager? = null
    private var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainViewModel.Factory()
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        checkPermissionsAndBegin()
    }

    private fun checkPermissionsAndBegin() {
        if (PermissionHelper.isLocationPermissionGranted(this)) {
            getLocation()
        } else {
            PermissionHelper.requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PermissionHelper.LOCATION_PERMISSION && PermissionHelper.permissionResultsAreGranted(grantResults)){
            getLocation()
        } else {
            checkPermissionsAndBegin()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            fetchData(location.longitude, location.latitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun fetchData(latitude: Double, longitude: Double){
        viewModel?.fetchWeatherInfo(latitude, longitude)
        viewModel?.getWeatherInfo()?.observe(this, Observer<Weather> {
            if (it != null) {
                showData(it)
            }
        })
    }

    private fun showData(it: Weather){
        progressBar.visibility = View.GONE
        latitude_text.text = it.latitude.toString()
        longitude_text.text = it.longitude.toString()
        timezone_text.text = it.timezone
        time_text.text = SimpleDateFormat("EEE, dd MMM yyyy, HH:mm:ss, Z").format(Date(it.currently!!.time * 1000))
        summary_text.text = it.currently!!.summary.toString()
        temperature_text.text = it.currently!!.temperature.toString() + " ℃"
        humidity_text.text = it.currently!!.humidity.toString()
        pressure_text.text = it.currently!!.pressure.toString()
        windSpeed_text.text = it.currently!!.windSpeed.toString()
        cloudCover_text.text = it.currently!!.cloudCover.toString()
        visibility_text.text = it.currently!!.visibility.toString()
    }

}
