package com.tracky.tracker.domain.usecase

import com.tracky.tracker.data.api.ApiResponse
import com.tracky.tracker.data.api.model.LoginResponse
import com.tracky.tracker.data.api.model.UserLoginInfo
import com.tracky.tracker.data.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userLoginInfo: UserLoginInfo): ApiResponse<LoginResponse> =
        withContext(Dispatchers.IO) {
            authRepository.login(userLoginInfo)
        }
}