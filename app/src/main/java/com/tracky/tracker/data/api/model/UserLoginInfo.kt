package com.tracky.tracker.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginInfo(val email: String, val password: String)