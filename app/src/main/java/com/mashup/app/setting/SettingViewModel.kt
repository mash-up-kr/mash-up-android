package com.mashup.app.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.repository.user.UserRepository
import com.mashup.util.Event

class SettingViewModel(
        private val userRepository: UserRepository
) : ViewModel() {
    private val _logoutEvent = MutableLiveData<Event<Boolean>>()
    val logoutEvent: LiveData<Event<Boolean>> = _logoutEvent

    private val _sendEmailEvent = MutableLiveData<Event<Boolean>>()
    val sendEmailEvent: LiveData<Event<Boolean>> = _sendEmailEvent

    fun sendFeedBackEmail() {
        _sendEmailEvent.value = Event(true)
    }

    fun onClickLogoutButton() {
        userRepository.logout()
        _logoutEvent.value = Event(true)
    }
}
