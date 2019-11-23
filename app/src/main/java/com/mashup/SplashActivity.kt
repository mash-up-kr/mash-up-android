package com.mashup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.app.notices.NoticesActivity
import com.mashup.repository.NoticesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val noticesRepository: NoticesRepository by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        compositeDisposable.add(
                noticesRepository
                        .getNoticeList(true)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            startActivity(Intent(this, NoticesActivity::class.java))
                            finish()
                        }, {
                            it.printStackTrace()
                        }))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
