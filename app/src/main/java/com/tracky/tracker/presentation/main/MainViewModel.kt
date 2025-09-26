package com.tracky.tracker.presentation.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tracky.tracker.data.repositories.AuthRepository
import com.tracky.tracker.data.service.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(authRepository.getToken() != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    init {
        // Mirror LocationService.locationFlow into the viewmodel's location state.
        viewModelScope.launch {
            LocationService.locationFlow.collect { loc ->
                _location.value = loc
            }
        }
    }

    fun logout() {
        authRepository.clearToken()
        _isLoggedIn.value = false
    }
}
