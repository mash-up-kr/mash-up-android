package com.mashup.model

data class User(
    val pk: Int,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val github: String,
    val userPeriodTeamSet: ArrayList<UserPeriodTeam>
)
