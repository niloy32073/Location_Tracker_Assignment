package com.tracky.tracker.data.service

import android.util.Log
import com.tracky.tracker.data.repositories.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class LocationRepository(
    private val client: HttpClient,
    private val authRepository: AuthRepository,
    private val baseUrl: String
) {
    suspend fun sendLocation(lat: Double, lng: Double): Boolean {
        val token = authRepository.getToken() ?: return false
        return try {
            val response = client.post("$baseUrl/driver/location") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(mapOf("lat" to lat, "lng" to lng))
            }
            if (response.status.isSuccess()) {
                Log.d("LocationService", "Location sent successfully")
            } else {
                Log.e("LocationService", "Failed to send location: $response")
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            Log.e("LocationRepository", "Error sending location: ${e.message}")
            false
        }
    }
}
