package com.mashup.app.attendees

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.model.NoticeAttendance

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<NoticeAttendance>) {
    (listView.adapter as AttendessDialogAdapter).submitList(items)
}