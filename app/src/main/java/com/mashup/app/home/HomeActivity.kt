package com.mashup.app.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mashup.R
import com.mashup.app.notice.NoticeActivity
import kotlinx.android.synthetic.main.content_home.*
import java.text.SimpleDateFormat

class HomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.recentNotice.observe(this, Observer {
            tvNoticeDate.text = viewModel.getNoticeTimeFormat(it.date)
            tvNoticedAt.text = viewModel.getNoticeTimeFormat(it.noticedAt)
            tvNoticeLocation.text = it.location.address
            tvNoticeContent.text = it.content
        })
        viewModel.getRecentNotice()

        content.setOnClickListener {
            startActivity(Intent(this, NoticeActivity::class.java))
            finish()
        }
    }
}