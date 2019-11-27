package com.mashup.app.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.R
import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.repository.notice.NoticesRepository
import com.mashup.repository.user.UserRepository
import com.mashup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(
        private val userRepository: UserRepository,
        private val noticesRepository: NoticesRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoginSuccess = MutableLiveData<Event<Boolean>>()
    val isLoginSuccess: LiveData<Event<Boolean>> = _isLoginSuccess

    private val _snackbarText = MutableLiveData<Event<Any>>()
    val snackbarText: LiveData<Event<Any>> = _snackbarText

    private val _hideKeyboard = MutableLiveData<Event<Boolean>>()
    val hideKeyboard: LiveData<Event<Boolean>> = _hideKeyboard

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private fun getAuthToken() {
        val currentEmail = email.value
        val currentPassword = password.value

        if (!currentEmail.isNullOrEmpty() && !currentPassword.isNullOrEmpty()) {
            compositeDisposable.add(
                    userRepository.getAuthToken(AuthTokenRequest("", currentEmail, currentPassword))
                            .subscribeOn(Schedulers.io())
                            .flatMap { noticesRepository.getNoticeList(true) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { _isLoginSuccess.value = Event(true) },
                                    {
                                        if (it.message.isNullOrEmpty()) {
                                            _snackbarText.value = Event(R.string.error_message_unknown)
                                        } else {
                                            _snackbarText.value = Event(it.message!!)
                                        }
                                    })
            )
        } else {
            _snackbarText.value = Event(R.string.login_error_message_empty_text)
        }
    }

    fun onClickLoginButton() {
        _hideKeyboard.value = Event(true)
        getAuthToken()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}