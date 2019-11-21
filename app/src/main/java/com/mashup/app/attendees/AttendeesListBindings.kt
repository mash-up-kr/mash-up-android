package com.mashup.app.attendees

import android.text.Html
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.R
import com.mashup.model.VoteStatus

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<AttendeesItem>) {
    (listView.adapter as AttendeesAdapter).submitList(items)
}

@BindingAdapter(value = ["app:voteCount", "app:voteStatus"])
fun setAttendeesSectionTitle(textView: TextView, voteCount: Int, voteStatus: VoteStatus) {
    @StringRes val resId = when (voteStatus) {
        VoteStatus.ATTEND -> R.string.attendees_tv_attendance_format
        VoteStatus.ABSENT -> R.string.attendees_tv_absent_format
        else -> R.string.attendees_tv_unselected_format
    }
    textView.text = Html.fromHtml(String.format(textView.resources.getString(resId, voteCount)))
}
