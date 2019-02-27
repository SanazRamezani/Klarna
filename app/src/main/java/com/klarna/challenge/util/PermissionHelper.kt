package com.klarna.challenge.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class PermissionHelper {

    companion object {

        private val GRANTED = PackageManager.PERMISSION_GRANTED
        val LOCATION_PERMISSION = 4

        fun isLocationPermissionGranted(activity: Activity): Boolean {
            val fine = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            val course = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            return isLowerThanAndroidM() || fine == GRANTED && course == GRANTED
        }

        fun requestLocationPermission(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION
            )
        }

        fun permissionResultsAreGranted(grantResults: IntArray): Boolean {
            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
            return true
        }

        private fun isLowerThanAndroidM(): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
        }
    }

}