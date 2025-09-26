package com.tracky.tracker.data.api

import com.tracky.tracker.data.api.model.ErrorDetails
import com.tracky.tracker.data.api.model.ErrorResponse
import com.tracky.tracker.data.api.model.LoginResponse
import com.tracky.tracker.data.api.model.UserLoginInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthApiService(
    private val client: HttpClient,
    private val baseUrl: String
) {
    // Login API call
    suspend fun login(userLoginInfo: UserLoginInfo): ApiResponse<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("$baseUrl/auth/login") {
                    contentType(ContentType.Application.Json)
                    setBody(userLoginInfo)
                }

                if (response.status.isSuccess()) {
                    ApiResponse.Success(response.body())
                } else {
                    ApiResponse.Error(errorResponse = response.body())
                }
            } catch (e: Exception) {
                ApiResponse.Error(
                    ErrorResponse(
                        message = e.message ?: "Network error",
                    )
                )
            }
        }
    }
}
