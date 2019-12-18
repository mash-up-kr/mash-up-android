package com.mashup.api.notice.request

import com.google.gson.annotations.SerializedName
import com.mashup.model.VoteStatus

data class AttendanceUpdateRequest(
        @SerializedName("noticePk") val noticePk: Int,
        @SerializedName("vote") val voteStatus: VoteStatus
)