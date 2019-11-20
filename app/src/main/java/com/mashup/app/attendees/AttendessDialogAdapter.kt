package com.mashup.app.attendees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.databinding.AttendeesDialogItemBinding
import com.mashup.model.NoticeAttendance

class AttendessDialogAdapter(private val viewModel: AttendeesDialogViewModel) :
    ListAdapter<NoticeAttendance, AttendessDialogAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: AttendeesDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: AttendeesDialogViewModel, item: NoticeAttendance) {

            binding.viewmodel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AttendeesDialogItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback : DiffUtil.ItemCallback<NoticeAttendance>() {
    override fun areItemsTheSame(oldItem: NoticeAttendance, newItem: NoticeAttendance): Boolean {
        return oldItem.pk == newItem.pk
    }

    override fun areContentsTheSame(oldItem: NoticeAttendance, newItem: NoticeAttendance): Boolean {
        return oldItem == newItem
    }
}