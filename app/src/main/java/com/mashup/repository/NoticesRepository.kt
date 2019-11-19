package com.mashup.repository

import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import io.reactivex.Completable
import io.reactivex.Flowable

interface NoticesRepository {

    fun getNoticeList(): Flowable<List<Notice>>

    fun updateNoticeAttendance(userId: Int, voteStatus: VoteStatus): Completable
}
