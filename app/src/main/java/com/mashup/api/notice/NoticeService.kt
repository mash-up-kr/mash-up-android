package com.mashup.api.notice

import com.mashup.model.Notice
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeService {

    @GET("/notice/list?size=10")
    fun getNotice(@Query("type") type: String, @Query("page") page: Int): Flowable<List<Notice>>


    @GET("/notice/active/list?type=public&size=10")
    fun getRecentPublicNotice(): Flowable<List<Notice>>
}