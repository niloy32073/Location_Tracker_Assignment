package com.tracky.tracker.data.repositories

import android.content.Context
import com.tracky.tracker.data.api.ApiResponse
import com.tracky.tracker.data.api.model.LoginResponse
import com.tracky.tracker.data.api.model.UserLoginInfo
import com.tracky.tracker.data.api.AuthApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import androidx.core.content.edit

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val context: Context
) : AuthRepository {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // Perform login and save token if successful
    override suspend fun login(userLoginInfo: UserLoginInfo): ApiResponse<LoginResponse> {
        return authApiService.login(userLoginInfo).also { response ->
            if (response is ApiResponse.Success) {
                prefs.edit { putString("token", response.data.token) }
            }
        }
    }

    override fun getToken(): String? = prefs.getString("token", null)

    override fun clearToken() {
        prefs.edit { clear() }
    }
}
