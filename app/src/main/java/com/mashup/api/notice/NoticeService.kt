package com.mashup.api.notice

import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface NoticeService {

    @GET("api/v1/notice/list?size=10")
    fun getNotice(@Query("type") type: String, @Query("page") page: Int): Flowable<List<Notice>>


    @GET("api/v1/notice/active/list?type=public&size=10")
    fun getRecentPublicNotice(): Flowable<List<Notice>>

    @GET("api/notices/")
    fun getNoticeList(): Flowable<List<Notice>>

    @PATCH("api/notices/attendances/{id}/")
    fun updateNoticeAttendance(@Path("id") userId: Int, @Body voteStatus: VoteStatus): Completable

}