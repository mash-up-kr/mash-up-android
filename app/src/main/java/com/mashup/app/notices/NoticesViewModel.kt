package com.mashup.app.notices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.api.MashupClient
import com.mashup.api.notice.NoticeService
import com.mashup.model.Notice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoticesViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Notice>>().apply { value = emptyList() }
    val items: LiveData<List<Notice>> = _items

    init {
        getNotice()
    }

    private val compositeDisposable = CompositeDisposable()

    private fun getNotice() {
        val dummyUserId = 1
        compositeDisposable.add(MashupClient.getService(NoticeService::class.java)
                .getNoticeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _items.value = it.map { notice ->
                        notice.attendanceSet.find { noticeAttendance -> noticeAttendance.user.pk == dummyUserId }?.let { attendance ->
                            notice.apply { notice.userAttendance = attendance.vote }
                        } ?: notice
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }
}
