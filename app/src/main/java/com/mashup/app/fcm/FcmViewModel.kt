package com.mashup.app.fcm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mashup.api.fcm.request.FcmRegistRequest
import com.mashup.repository.fcm.FcmRepository
import com.mashup.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FcmViewModel(
    private val userRepository: UserRepository,
    private val fcmRepository: FcmRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun start() {
        if (fcmRepository.getFcmToken() == null) {
            userRepository.getCachedAuthToken()?.let {
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w("loloss", "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }

                        // Get new Instance ID token
                        val token = task.result?.token
                        token?.run {
                            compositeDisposable.add(
                                fcmRepository.registFcm(it.key, FcmRegistRequest(this))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        Log.d("loloss", it.toString())
                                    }, {
                                        it.printStackTrace()
                                    })
                            )
                        }
                    })
                //fcmRepository.sendTestNoti(it.key, FcmTest("test", "hello"))
            }
        }/* else {
            userRepository.getCachedAuthToken()?.let {
                compositeDisposable.add(
                    fcmRepository.sendTestNoti(
                        it.key,
                        FcmTest("hello", fcmRepository.getFcmToken()!!)
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                        }, {
                            it.printStackTrace()
                        })
                )
            }
        }*/
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}