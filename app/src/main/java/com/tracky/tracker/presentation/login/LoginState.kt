package com.tracky.tracker.presentation.login

import com.tracky.tracker.data.api.model.LoginResponse

// UI state for login screen
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}
