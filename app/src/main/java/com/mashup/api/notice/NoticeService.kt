package com.mashup.api.notice

import com.mashup.model.Notice
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeService {

    @GET("api/v1/notice/list?size=10")
    fun getNotice(@Query("type") type: String, @Query("page") page: Int): Flowable<List<Notice>>


    @GET("api/v1/notice/active/list?type=public&size=10")
    fun getRecentPublicNotice(): Flowable<List<Notice>>

    @GET("api/notices/")
    fun getNoticeList(): Flowable<List<Notice>>
}