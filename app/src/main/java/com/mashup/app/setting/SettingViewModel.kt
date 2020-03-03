package com.mashup.app.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.repository.fcm.FcmRepository
import com.mashup.repository.user.UserRepository
import com.mashup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingViewModel(
    private val userRepository: UserRepository,
    private val fcmRepository: FcmRepository
) : ViewModel() {
    private val _logoutEvent = MutableLiveData<Event<Boolean>>()
    val logoutEvent: LiveData<Event<Boolean>> = _logoutEvent

    private val _sendEmailEvent = MutableLiveData<Event<Boolean>>()
    val sendEmailEvent: LiveData<Event<Boolean>> = _sendEmailEvent

    fun sendFeedBackEmail() {
        _sendEmailEvent.value = Event(true)
    }

    private val compositeDisposable = CompositeDisposable()

    fun onClickLogoutButton() {
        with(userRepository.getCachedAuthToken()) {
            if (this != null) {
                compositeDisposable.add(
                    fcmRepository.removeFcmRegist(this.key)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            userRepository.logout()
                            _logoutEvent.value = Event(true)
                        }, {
                            it.printStackTrace()
                        })
                )
            } else {
                userRepository.logout()
                _logoutEvent.value = Event(true)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
