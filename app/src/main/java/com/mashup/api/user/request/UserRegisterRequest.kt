package com.mashup.api.user.request

import com.google.gson.annotations.SerializedName
import com.mashup.model.Team

data class UserRegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("team") val team: Team
)