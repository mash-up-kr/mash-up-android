package com.mashup.app.notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.api.MashupClient
import com.mashup.api.notice.NoticeService
import com.mashup.api.user.UserService
import com.mashup.api.user.request.UserRegisterRequest
import com.mashup.model.Notice
import com.mashup.model.Team
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NoticeViewModel: ViewModel() {

    val noticeData = MutableLiveData<List<Notice>>()

    private var noticeDisposable: Disposable? = null

    fun getNotice() {
        noticeDisposable?.dispose()
        noticeDisposable = MashupClient.getService(NoticeService::class.java)
            .getNotice(type = "public", page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                noticeData.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

}