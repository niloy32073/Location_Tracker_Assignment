package com.tracky.tracker.data.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.tracky.tracker.App
import com.tracky.tracker.R
import com.tracky.tracker.data.repositories.AuthRepository
import com.tracky.tracker.domain.usecase.SendLocationUseCase
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.getKoin

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val sendLocationUseCase: SendLocationUseCase by lazy {
        (application as App).getKoin().get<SendLocationUseCase>()
    }

    companion object {
        val locationFlow = MutableStateFlow<Location?>(null)
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val token = (application as App).getKoin().get<AuthRepository>().getToken()
        if (token.isNullOrEmpty()) {
            stopSelf() // Stop if not logged in
            return
        }

        locationRequest = LocationRequest.create().apply {
            interval = 10_000
            fastestInterval = 5_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        startForeground(1, createNotification())
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location = result.lastLocation ?: return
                    locationFlow.value = location
                    coroutineScope.launch {
                        sendLocationUseCase(location.latitude, location.longitude)
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun createNotification(): Notification {
        val channelId = "location_channel"
        val channel = NotificationChannel(
            channelId, "Location Service", NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Tracking Location")
            .setContentText("Your location is being tracked")
            .setSmallIcon(R.drawable.location) // replace with your app icon
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
