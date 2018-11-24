package com.mashup.app.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mashup.R
import com.mashup.model.Notice

class NoticeAdapter(private val noticeList: ArrayList<Notice>) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
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

            tvNoticeDatestamp.text = notice.createdAt.toString()
            tvNoticeTime.text = notice.date.toString()
            tvNoticeSpace.text = notice.location.toString()
            tvNoticeContent.text = notice.description
        }
    }

    interface OnClickListener {
        fun onItemClick(notice: Notice, position: Int)
        fun onItemDelete(notice: Notice)
    }
}