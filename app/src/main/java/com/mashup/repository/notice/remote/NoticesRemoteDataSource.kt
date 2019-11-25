package com.mashup.repository.notice.remote

import com.mashup.api.notice.NoticeService
import com.mashup.api.notice.request.AttendanceUpdateRequest
import com.mashup.model.VoteStatus
import com.mashup.api.notice.response.NoticeListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class NoticesRemoteDataSource internal constructor(
        private val noticeService: NoticeService
) {
    fun getNoticeList(): Flowable<NoticeListResponse> =
            noticeService.getNoticeList().subscribeOn(Schedulers.io())

    fun updateNoticeAttendance(token: String, noticeId: Int, voteStatus: VoteStatus): Completable =
            noticeService.updateNoticeAttendance("Token $token", noticeId, AttendanceUpdateRequest(voteStatus)).subscribeOn(Schedulers.io())
}
