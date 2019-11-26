package com.mashup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mashup.app.login.LoginActivity
import com.mashup.app.main.MainActivity
import com.mashup.repository.notice.NoticesRepository
import com.mashup.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val userRepository: UserRepository by inject()
    private val noticesRepository: NoticesRepository by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (userRepository.hasAuthToken()) {
            compositeDisposable.add(
                    noticesRepository
                            .getNoticeList(true)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }, {
                                it.printStackTrace()
                            })
            )
        } else {
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1500)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
