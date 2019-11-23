package com.mashup.repository.notice

import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import io.reactivex.Completable
import io.reactivex.Flowable

interface NoticesRepository {

    fun getNoticeList(forceRefresh: Boolean): Flowable<List<Notice>>

    fun updateNoticeAttendance(token: String, userId: Int, voteStatus: VoteStatus): Completable
}
