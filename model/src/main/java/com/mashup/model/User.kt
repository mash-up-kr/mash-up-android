package com.mashup.model

import com.google.gson.annotations.SerializedName


enum class Team {
    @SerializedName("android") ANDROID,
    @SerializedName("backend") BACKEND,
    @SerializedName("design") DESIGN,
    @SerializedName("ios") IOS
}

enum class Role {
    @SerializedName("READ") READ,
    @SerializedName("WRITE") WRITE
}

data class User (
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("email") var email: String = "",
    @SerializedName("base_number") var baseNumber: Int = 0,
    @SerializedName("exit") var exit: Boolean = false,
    @SerializedName("name") var name: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("role") var role: Role = Role.READ,
    @SerializedName("team") var team: Team = Team.ANDROID,
    @SerializedName("team_role") var teamRole: Role = Role.READ,
    @SerializedName("profile_img") val profileImage: String? = null
)