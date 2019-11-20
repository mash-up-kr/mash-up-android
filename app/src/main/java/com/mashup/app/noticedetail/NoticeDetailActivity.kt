package com.mashup.app.noticedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.R
import com.mashup.model.Notice

class NoticeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notice_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NoticeDetailFragment.newInstance(
                    intent.getParcelableExtra<Notice>(NoticeDetailFragment.EXTRA_NOTICE))
                )
                .commitNow()
        }
    }

}
