package com.mashup.repository

import com.mashup.model.Notice
import io.reactivex.Flowable

interface NoticesRepository {

    fun getNoticeList(): Flowable<List<Notice>>
}
