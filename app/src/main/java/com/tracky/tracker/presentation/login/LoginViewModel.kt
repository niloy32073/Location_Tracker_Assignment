package com.tracky.tracker.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tracky.tracker.data.api.ApiResponse
import com.tracky.tracker.data.api.model.LoginResponse
import com.tracky.tracker.data.api.model.UserLoginInfo
import com.tracky.tracker.data.repositories.AuthRepository
import com.tracky.tracker.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val response = loginUseCase(UserLoginInfo(email = email, password = password))
            _loginState.value = when (response) {
                is ApiResponse.Success -> LoginState.Success(response.data)
                is ApiResponse.Error -> LoginState.Error(response.errorResponse.message)
                is ApiResponse.Loading -> LoginState.Loading
            }
        }
    }
}

