package com.tracky.tracker.data.api

import com.tracky.tracker.data.api.model.ErrorResponse
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @Serializable
    data class Success<out T>(val data: T) : ApiResponse<T>()
    @Serializable
    data class Error(val errorResponse: ErrorResponse) : ApiResponse<Nothing>()
    @Serializable
    object Loading : ApiResponse<Nothing>()
}