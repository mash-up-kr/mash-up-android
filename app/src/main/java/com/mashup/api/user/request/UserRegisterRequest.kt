package com.mashup.api.user.request

import com.google.gson.annotations.SerializedName
import com.mashup.model.Team

data class UserRegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("team") val team: Team,
    @SerializedName("base_number") val baseNumber: Int
)