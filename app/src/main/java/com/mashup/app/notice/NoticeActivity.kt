package com.mashup.app.notice

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.R
import com.mashup.model.Location
import com.mashup.model.Notice
import kotlinx.android.synthetic.main.content_notice.*
import org.threeten.bp.LocalDateTime
import java.time.LocalDate

class NoticeActivity : AppCompatActivity(), NoticeAdapter.OnClickListener {

    private val noticeAdapter : NoticeAdapter
        get() = rvNotice.adapter as NoticeAdapter

    val noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel::class.java)


    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        initNoticeList()
        getNotices()
    }

    fun initNoticeList(){
        rvNotice.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = NoticeAdapter(ArrayList())
        adapter.setListener(this)
        rvNotice.adapter = adapter
    }

    fun getNotices() {
        noticeViewModel.noticeData.observe(this, Observer {
            noticeAdapter.noticeList = it
        })
        noticeViewModel.getRecentPublicNotice()
    }

    override fun onItemClick(notice: Notice, position: Int) {

    }

    override fun onItemDelete(notice: Notice) {

    }

}
