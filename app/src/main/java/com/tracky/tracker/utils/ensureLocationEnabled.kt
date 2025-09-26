package com.tracky.tracker.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast

fun ensureLocationEnabled(context: Context, onEnabled: () -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    if (isEnabled) {
        onEnabled()
    } else {
        Toast.makeText(context, "Please enable location", Toast.LENGTH_LONG).show()
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}