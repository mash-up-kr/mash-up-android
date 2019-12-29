package com.mashup.app.notices

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.R
import com.mashup.databinding.NoticeItemBinding
import com.mashup.model.Notice

class NoticeAdapter(private val viewModel: NoticesViewModel) :
    ListAdapter<Notice, NoticeAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: NoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NoticesViewModel, item: Notice, position: Int) {

            if (position == viewModel.expandPosition) {
                binding.descriptionText.maxLines = 100
                binding.toggleButton.text =
                    binding.toggleButton.context.getString(R.string.notice_collapse)
            } else {
                binding.descriptionText.maxLines = 5
                binding.toggleButton.text =
                    binding.toggleButton.context.getString(R.string.notice_see_more)
            }
            binding.viewmodel = viewModel
            binding.notice = item
            binding.position = position
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoticeItemBinding.inflate(layoutInflater, parent, false)

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
class TaskDiffCallback : DiffUtil.ItemCallback<Notice>() {
    override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return oldItem.pk == newItem.pk
    }

    override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return oldItem == newItem
    }
}