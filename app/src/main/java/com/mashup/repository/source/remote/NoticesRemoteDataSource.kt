package com.mashup.repository.source.remote

import com.mashup.api.notice.NoticeService
import com.mashup.model.Notice
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class RepositoriesRemoteDataSource internal constructor(
        private val noticeService: NoticeService
) {
    fun getNoticeList(): Flowable<List<Notice>> =
            noticeService.getNoticeList().subscribeOn(Schedulers.io())
}
