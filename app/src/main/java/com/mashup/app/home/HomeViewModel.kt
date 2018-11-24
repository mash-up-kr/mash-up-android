package com.mashup.app.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.api.MashupClient
import com.mashup.api.notice.NoticeService
import com.mashup.model.Notice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime

class HomeViewModel: ViewModel() {

    val recentNotice = MutableLiveData<Notice>()

    private var getRecentNoticeDisposable: Disposable? = null

    fun getRecentNotice() {
        getRecentNoticeDisposable?.dispose()
        getRecentNoticeDisposable = MashupClient.getService(NoticeService::class.java)
            .getRecentPublicNotice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.takeIf { it.isNotEmpty() }?.apply {
                    recentNotice.postValue(get(0))
                } ?: return@subscribe
            }
    }

    fun getNoticeTimeFormat(localDateTime : LocalDateTime) : String {

        var noticeTime : String = localDateTime.year.toString() + "년 " +
                localDateTime.monthValue.toString() + "월 " +
                localDateTime.dayOfMonth.toString() + "일" +
                "(" + localDateTime.dayOfWeek + ") " +
                localDateTime.hour + "시 " +
                localDateTime.minute + "분 "

        return noticeTime
    }

}