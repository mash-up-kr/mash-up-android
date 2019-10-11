package com.mashup.app.notices

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.model.Notice
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Notice>) {
    (listView.adapter as NoticeAdapter).submitList(items)
}

@BindingAdapter("app:noticeTime")
fun setNoticeTimeFormat(textView: TextView, date: Date) {
    textView.text = DateTimeFormat.forPattern("yyyy년 M월 dd일 (E)").print(DateTime(date))
}

@BindingAdapter(value = ["app:startAt", "app:duration"])
fun setNoticeDurationFormat(textView: TextView, startAt: Date, duration: String) {
    val startDate = DateTime(startAt)
    val endDate = startDate.plusHours(DateTimeFormat.forPattern("HH:mm:ss").parseLocalTime(duration).hourOfDay)
    textView.text = "${DateTimeFormat.forPattern("a h시").print(startDate)}-${DateTimeFormat.forPattern("h시").print(endDate)}"
}