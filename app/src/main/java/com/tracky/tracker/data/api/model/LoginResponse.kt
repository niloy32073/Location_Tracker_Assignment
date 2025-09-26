package com.tracky.tracker.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: User
)