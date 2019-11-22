package com.mashup.app.notices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.model.Notice
import com.mashup.model.NoticeAttendance
import com.mashup.model.VoteStatus
import com.mashup.model.mapToPresentation
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

    private val _showDetailEvent = MutableLiveData<Event<Notice>>()
    val showDetailEvent: LiveData<Event<Notice>> = _showDetailEvent

    private val _showAttendeesEvent = MutableLiveData<Event<List<NoticeAttendance>>>()
    val showAttendeesEvent: LiveData<Event<List<NoticeAttendance>>> = _showAttendeesEvent

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
                                notice.mapToPresentation(dummyUserId)
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
                        .subscribe({ updateList(noticeId, VoteStatus.ATTEND) },{ updateList(noticeId, VoteStatus.ATTEND) })//TODO 예외 처리로 돌려얗함
        )
    }

    fun onClickAbsentButton(noticeId: Int) {
        compositeDisposable.add(
                noticesRepository.updateNoticeAttendance(dummyUserId, VoteStatus.ABSENT)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ updateList(noticeId, VoteStatus.ABSENT) }, { updateList(noticeId, VoteStatus.ABSENT) })
        )
    }

    fun onClickItem(notice: Notice) {
        _showDetailEvent.value = Event(notice)
    }

    fun onClickAttendeesButton(attendees: List<NoticeAttendance>) {
        _showAttendeesEvent.value = Event(attendees)
    }

    private fun updateList(noticeId: Int, voteStatus: VoteStatus) {
        var position = 0
        _items.value?.apply {
            map {notice ->
                position++
                if (notice.pk == noticeId) {
                    notice.vote(voteStatus)
                    /* TODO 서버값을 다시 불러올지 뷰만 업데이트 해줄지 선택 해야함*/
                    notice.attendanceSet.find { it.user.pk == dummyUserId }?.apply { vote = voteStatus }

                    _itemChangedEvent.value = Event(position - 1)
                } else {
                    notice
                }
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
