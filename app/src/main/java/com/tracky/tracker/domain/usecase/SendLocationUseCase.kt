package com.tracky.tracker.domain.usecase

import com.tracky.tracker.data.service.LocationRepository

class SendLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(lat: Double, lng: Double): Boolean {
        return repository.sendLocation(lat, lng)
    }
}
