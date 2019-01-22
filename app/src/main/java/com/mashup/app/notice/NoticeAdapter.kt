package com.mashup.app.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mashup.R
import com.mashup.model.Notice
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat

class NoticeAdapter(var noticeList: List<Notice>) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
    private var listener: OnClickListener? = null

    fun setListener(clickListener: OnClickListener) {
        this.listener = clickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo: Notice = noticeList[position]
        holder.bindItems(todo)

        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null) {
                listener!!.onItemClick(todo, position)
            }
        })

    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notice_list_item, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(notice: Notice) {
            val tvNoticeDatestamp = itemView.findViewById<TextView>(R.id.tvNoticeDatestamp)
            val tvNoticeTime = itemView.findViewById<TextView>(R.id.tvNoticeTime)
            val tvNoticeSpace = itemView.findViewById<TextView>(R.id.tvNoticeSpace)
            val tvNoticeContent = itemView.findViewById<TextView>(R.id.tvNoticeContent)

            tvNoticeDatestamp.text = getNoticeTimeFormat(notice.noticedAt)
            tvNoticeTime.text = getNoticeTimeFormat(notice.date)
            tvNoticeSpace.text = notice.location.address
            tvNoticeContent.text = notice.content
        }

        fun getNoticeTimeFormat(localDateTime : LocalDateTime) : String {

            var noticeTime : String = localDateTime.year.toString() + "년 " +
                    localDateTime.monthValue.toString() + "월 " +
                    localDateTime.dayOfMonth.toString() + "일" +
                    "(" + localDateTime.dayOfWeek + ") " +
                    localDateTime.hour + "시 " +
                    localDateTime.minute + "분 "

            return noticeTime
        }
    }

    interface OnClickListener {
        fun onItemClick(notice: Notice, position: Int)
        fun onItemDelete(notice: Notice)
    }
}