package com.mashup.app.notices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.R
import com.mashup.model.*
import com.mashup.repository.notice.NoticesRepository
import com.mashup.repository.user.UserRepository
import com.mashup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoticesViewModel(
        private val noticesRepository: NoticesRepository,
        private val userRepository: UserRepository
) : ViewModel() {
    private val _items = MutableLiveData<List<Notice>>().apply { value = emptyList() }
    val items: LiveData<List<Notice>> = _items

    private val _onException = MutableLiveData<Event<Boolean>>()
    val onException: LiveData<Event<Boolean>> = _onException

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _itemChangedEvent = MutableLiveData<Event<Int>>()
    val itemChangedEvent: LiveData<Event<Int>> = _itemChangedEvent

    private val _showDetailEvent = MutableLiveData<Event<Notice>>()
    val showDetailEvent: LiveData<Event<Notice>> = _showDetailEvent

    private val _showAttendeesEvent = MutableLiveData<Event<List<NoticeAttendance>>>()
    val showAttendeesEvent: LiveData<Event<List<NoticeAttendance>>> = _showAttendeesEvent

    private val _snackbarText = MutableLiveData<Event<Any>>()
    val snackbarText: LiveData<Event<Any>> = _snackbarText

    private val compositeDisposable = CompositeDisposable()

    private lateinit var authToken: AuthToken

    var expandPosition: Int = -1

    init {
        checkAuthToken()
        getNotice(false)
    }

    private fun checkAuthToken() {
        with(userRepository.getCachedAuthToken()) {
            if (this == null)
                _onException.value = Event(true)
            else
                authToken = this
        }
    }

    private fun getNotice(forceRefresh: Boolean) {
        compositeDisposable.add(
                noticesRepository
                        .getNoticeList(forceRefresh)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            _items.value = it.map { notice ->
                                notice.mapToPresentation(authToken.user.pk)
                            }
                            _isLoading.value = false
                        }, {
                            it.printStackTrace()
                            _isLoading.value = false
                            /* TODO 에러 발생 분기 처리 */
                        })
        )
    }

    fun onClickAttendanceButton(noticeId: Int, voteStatus: VoteStatus) {
        compositeDisposable.add(
                noticesRepository.updateNoticeAttendance(authToken.key, noticeId, voteStatus)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { updateList(noticeId, voteStatus) },
                                {
                                    if (it.message.isNullOrEmpty()) {
                                        _snackbarText.value = Event(R.string.error_message_unknown)
                                    } else {
                                        _snackbarText.value = Event(it.message!!)
                                    }
                                })//TODO 예외 처리로 돌려얗함
        )
    }

    fun onClickItem(notice: Notice) {
        _showDetailEvent.value = Event(notice)
    }

    fun onClickAttendeesButton(attendees: List<NoticeAttendance>) {
        _showAttendeesEvent.value = Event(attendees)
    }

    fun onRefresh() {
        getNotice(true)
    }

    private fun updateList(noticeId: Int, voteStatus: VoteStatus) {
        var position = 0
        _items.value?.apply {
            map { notice ->
                position++
                if (notice.pk == noticeId) {
                    notice.vote(voteStatus)
                    /* TODO 서버값을 다시 불러올지 뷰만 업데이트 해줄지 선택 해야함*/
                    notice.attendanceSet.find { it.user.pk == authToken.user.pk }?.apply { vote = voteStatus }

                    _itemChangedEvent.value = Event(position - 1)
                } else {
                    notice
                }
            }
        }
    }

    val setExpandPosition: (Int) -> Unit
        get() = fun(position: Int) {
            expandPosition = if (expandPosition == position) {
                -1
            } else {
                position
            }
        }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
