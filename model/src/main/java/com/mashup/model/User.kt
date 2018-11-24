package com.mashup.model

import org.threeten.bp.LocalDate


enum class Team {

    DESIGN,
    ANDROID,
    IOS,
    BACKEND
}

enum class Status {
    ACTIVATED, EXITED, DEACTIVATED,
}

enum class Role {
    MEMBER, VICE_TEAM_LEADER, TEAM_LEADER, GENERAL_AFFAIRS, VICE_CHIEF, CHIEF
}

data class User (
    val id: Int,
    val name: String,
    val description: String,
    val profileImage: String,
    val team: Team,
    val joinedAt: LocalDate,
    val exitedAt: LocalDate?,
    val status: Status,
    val role: Role,
    val baseNumber: Int
)