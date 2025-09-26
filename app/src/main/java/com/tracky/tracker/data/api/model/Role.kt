package com.tracky.tracker.data.api.model

import kotlinx.serialization.Serializable
@Serializable
data class Role(
    val id: Int,
    val role: String,
    val permissions: List<String>
)