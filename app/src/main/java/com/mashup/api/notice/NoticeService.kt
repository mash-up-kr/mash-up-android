package com.mashup.api.notice

import com.mashup.api.notice.request.AttendanceUpdateRequest
import com.mashup.repository.notice.remote.response.NoticeListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface NoticeService {

    @GET("api/notices/")
    fun getNoticeList(): Flowable<NoticeListResponse>

    @PATCH("api/notices/attendances/{id}/")
    fun updateNoticeAttendance(@Header("Authorization") token: String, @Path("id") noticeId: Int, @Body attendanceUpdateRequest: AttendanceUpdateRequest): Completable

}