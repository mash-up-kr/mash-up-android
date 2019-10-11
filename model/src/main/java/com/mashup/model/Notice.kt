package com.mashup.model

import java.util.*

data class Notice(
    val pk: Int,
    val team: Team,
    val title: String,
    val author: User,
    val startAt: Date,
    val duration: String,
    val address1: String,
    val address2: String,
    val description: String,
    val attendanceSet: ArrayList<NoticeAttendance>,
    var userAttendance: VoteStatus = VoteStatus.NONE
)
