package com.mik2003.boukennihon.core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object LocationUtils {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun initializeFusedLocationProvider(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastKnownLocation(context: Context, onSuccess: (location: android.location.Location) -> Unit, onFailure: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, attempt to retrieve the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        // Location retrieved successfully, invoke the success callback
                        onSuccess(location)
                    } else {
                        // Last known location is not available, handle accordingly
                        onFailure()
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    onFailure()
                }
        } else {
            // Permission is not granted, handle accordingly
            onFailure()
        }
    }
}
