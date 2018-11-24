package com.mashup.app.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.api.MashupClient
import com.mashup.api.user.UserService
import com.mashup.api.user.request.UserRegisterRequest
import com.mashup.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel: ViewModel() {

    val userData = MutableLiveData<User>()

    private var registerDisposable: Disposable? = null

    fun register(email: String, password: String) {
        registerDisposable?.dispose()
        registerDisposable = MashupClient.getService(UserService::class.java)
            .register(UserRegisterRequest(email = email, password = password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userData.postValue(it) }
    }

}