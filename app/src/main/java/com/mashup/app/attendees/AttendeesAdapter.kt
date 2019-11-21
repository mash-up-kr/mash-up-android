package com.mashup.app.attendees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.databinding.AttendeesItemBinding
import com.mashup.databinding.AttendeesSectionItemBinding
import com.mashup.model.NoticeAttendance
import com.mashup.model.VoteStatus

class AttendeesAdapter :
    ListAdapter<AttendeesItem, RecyclerView.ViewHolder>(AttendeesDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item.type == ItemType.NORMAL)
            (holder as ItemViewHolder).bind(item.item as NoticeAttendance)
        else
            (holder as SectionViewHolder).bind(item.item as Pair<Int, VoteStatus>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemType.NORMAL.typeInt)
            ItemViewHolder.from(parent)
        else
            SectionViewHolder.from(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.typeInt
    }

    class ItemViewHolder private constructor(val binding: AttendeesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoticeAttendance) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AttendeesItemBinding.inflate(layoutInflater, parent, false)

                return ItemViewHolder(binding)
            }
        }
    }

    class SectionViewHolder private constructor(val binding: AttendeesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pair: Pair<Int, VoteStatus>) {
            binding.count = pair.first
            binding.status = pair.second
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AttendeesSectionItemBinding.inflate(layoutInflater, parent, false)

                return SectionViewHolder(binding)
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
class AttendeesDiffCallback : DiffUtil.ItemCallback<AttendeesItem>() {
    override fun areItemsTheSame(oldItem: AttendeesItem, newItem: AttendeesItem): Boolean {
        return oldItem.type == newItem.type && oldItem.item == newItem.item
    }

    override fun areContentsTheSame(oldItem: AttendeesItem, newItem: AttendeesItem): Boolean {
        return false
    }
}
