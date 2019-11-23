package com.mashup.repository.notice.remote.response

import com.mashup.model.Notice

data class NoticeListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Notice>
)