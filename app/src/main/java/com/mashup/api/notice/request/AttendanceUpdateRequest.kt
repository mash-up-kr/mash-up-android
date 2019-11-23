package com.mashup.api.notice.request

import com.google.gson.annotations.SerializedName
import com.mashup.model.VoteStatus

data class AttendanceUpdateRequest(
        @SerializedName("vote") val voteStatus: VoteStatus
)