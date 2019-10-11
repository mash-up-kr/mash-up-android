package com.mashup.model

import com.google.gson.annotations.SerializedName

enum class VoteStatus {
    @SerializedName("unselected")
    UNSELECTED,
    @SerializedName("attend")
    ATTEND,
    @SerializedName("absent")
    ABSENT,
    @SerializedName("late")
    LATE,
    NONE
}

data class NoticeAttendance(
    val pk:Int,
    val user: User,
    val vote: VoteStatus,
    val voteDisplay: String
)
