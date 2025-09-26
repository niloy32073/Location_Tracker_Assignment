package com.tracky.tracker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val email: String,
    @SerialName("role_id")
    val roleId: Int,
    val color: Int,
    val role: Role,
    val permissions: List<String>,
    @SerialName("account_type")
    val accountType: Int,
    @SerialName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerialName("disabled_reminders")
    val disabledReminders: Int,
    @SerialName("alert_new_planning_sale")
    val alertNewPlanningSale: Int
)