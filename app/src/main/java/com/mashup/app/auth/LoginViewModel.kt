package com.mashup.app.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider
import com.mashup.api.MashupClient
import com.mashup.api.user.UserService
import com.mashup.api.user.request.UserRegisterRequest
import com.mashup.model.Team
import com.mashup.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.security.auth.callback.Callback

class LoginViewModel: ViewModel() {

    val userData = MutableLiveData<User>()

    private var registerDisposable: Disposable? = null

    fun register(email: String, password: String, name: String, team: Team) {
        registerDisposable?.dispose()
        registerDisposable = MashupClient.getService(UserService::class.java)
            .register(UserRegisterRequest(email = email, password = password, name = name, team = team))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userData.postValue(it) }
    }

    fun phoneAuth(phoneNumber : String, callbacks : Callback){

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//            phoneNumber,      // Phone number to verify
//            60,               // Timeout duration
//            TimeUnit.SECONDS, // Unit of timeout
//            this,             // Activity (for callback binding)
//            callbacks) // OnVerificationStateChangedCallbacks
    }

}