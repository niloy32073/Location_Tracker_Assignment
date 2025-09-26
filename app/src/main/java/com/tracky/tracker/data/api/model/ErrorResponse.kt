package com.tracky.tracker.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)