package com.mashup.app.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.R
import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.repository.user.UserRepository
import com.mashup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(
        private val repository: UserRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoginSuccess = MutableLiveData<Event<Boolean>>()
    val isLoginSuccess: LiveData<Event<Boolean>> = _isLoginSuccess

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private fun getAuthToken() {
        val currentEmail = email.value
        val currentPassword = password.value

        if (!currentEmail.isNullOrEmpty() && !currentPassword.isNullOrEmpty()) {
            compositeDisposable.add(
                    repository.getAuthToken(AuthTokenRequest("", currentEmail, currentPassword))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ _isLoginSuccess.value = Event(true) },
                                    { _snackbarText.value = Event(R.string.login_error_message_fail_login) })
            )
        } else {
            _snackbarText.value = Event(R.string.login_error_message_empty_text)
        }
    }

    fun onClickLoginButton() {
        getAuthToken()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}