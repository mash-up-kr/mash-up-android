package com.mashup.api.notice

import com.mashup.api.notice.request.AttendanceUpdateRequest
import com.mashup.api.notice.response.NoticeListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface NoticeService {

    @GET("api/notices/")
    fun getNoticeList(): Flowable<NoticeListResponse>

    @Headers("content-type: application/json")
    @PATCH("api/notices/attendances/")
    fun updateNoticeAttendance(@Header("Authorization") token: String, @Body attendanceUpdateRequest: AttendanceUpdateRequest): Completable

}