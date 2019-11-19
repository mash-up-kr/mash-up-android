package com.mashup.app.notices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.model.Notice
import com.mashup.model.VoteStatus
import com.mashup.repository.NoticesRepository
import com.mashup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoticesViewModel(
        private val noticesRepository: NoticesRepository
) : ViewModel() {
    private val _items = MutableLiveData<List<Notice>>().apply { value = emptyList() }
    val items: LiveData<List<Notice>> = _items

    private val _itemChangedEvent = MutableLiveData<Event<Int>>()
    val itemChangedEvent: LiveData<Event<Int>> = _itemChangedEvent

    private val compositeDisposable = CompositeDisposable()
    private val dummyUserId = 1

    init {
        getNotice()
    }

    private fun getNotice() {
        compositeDisposable.add(
                noticesRepository
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
                            /* TODO 에러 발생 분기 처리 */
                        })
        )
    }

    fun onClickAttendButton(noticeId: Int) {
        compositeDisposable.add(
                noticesRepository.updateNoticeAttendance(dummyUserId, VoteStatus.ATTEND)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ updateList(noticeId, VoteStatus.ATTEND) },{ /* TODO 에러 발생 분기 처리 */ })
        )
    }

    fun onClickAbsentButton(noticeId: Int) {
        compositeDisposable.add(
                noticesRepository.updateNoticeAttendance(dummyUserId, VoteStatus.ABSENT)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ updateList(noticeId, VoteStatus.ABSENT) }, { /* TODO 에러 발생 분기 처리 */ })
        )
    }

    private fun updateList(noticeId: Int, voteStatus: VoteStatus) {
        var position = 0
        _items.value?.apply {
            map {
                position++
                if (it.pk == noticeId) {
                    _itemChangedEvent.value = Event(position - 1)
                    it.userAttendance = voteStatus
                } else {
                    it
                }
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
