package com.tracky.tracker.data.repositories

import com.tracky.tracker.data.api.ApiResponse
import com.tracky.tracker.data.api.model.LoginResponse
import com.tracky.tracker.data.api.model.UserLoginInfo

interface AuthRepository {
    suspend fun login(userLoginInfo: UserLoginInfo): ApiResponse<LoginResponse>
    fun getToken(): String?
    fun clearToken()
}
