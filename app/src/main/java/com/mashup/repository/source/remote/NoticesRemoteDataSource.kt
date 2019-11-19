package com.mashup.repository.source.remote

import com.mashup.api.notice.NoticeService
import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class RepositoriesRemoteDataSource internal constructor(
        private val noticeService: NoticeService
) {
    fun getNoticeList(): Flowable<List<Notice>> =
            noticeService.getNoticeList().subscribeOn(Schedulers.io())

    fun updateNoticeAttendance(userId: Int, voteStatus: VoteStatus): Completable =
            noticeService.updateNoticeAttendance(userId, voteStatus).subscribeOn(Schedulers.io())
}
