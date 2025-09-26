package com.tracky.tracker.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetails(
    val name: String,
    val message: String
)