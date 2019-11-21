package com.mashup.app.attendees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.model.NoticeAttendance
import com.mashup.model.VoteStatus
import com.mashup.util.Event

class AttendeesViewModel(
        private val attendanceList: List<NoticeAttendance>
) : ViewModel() {
    private val _items = MutableLiveData<List<AttendeesItem>>().apply { value = emptyList() }
    val items: LiveData<List<AttendeesItem>> = _items

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: LiveData<Event<Boolean>> = _closeEvent

    init {
        setAttendanceList()
    }

    private fun setAttendanceList() {
        val attendList = ArrayList<AttendeesItem>()
        val absentList = ArrayList<AttendeesItem>()
        val unselectedList = ArrayList<AttendeesItem>()

        attendanceList.forEach {
            when {
                it.vote == VoteStatus.ATTEND -> attendList.add(AttendeesItem(ItemType.NORMAL, it))
                it.vote == VoteStatus.ABSENT -> absentList.add(AttendeesItem(ItemType.NORMAL, it))
                else -> unselectedList.add(AttendeesItem(ItemType.NORMAL, it))
            }
        }
        val array = ArrayList<AttendeesItem>()
        if (attendList.size > 0) {
            array.add(AttendeesItem(ItemType.SECTION, Pair(attendList.size, VoteStatus.ATTEND)))
            array.addAll(attendList)
        }
        if (absentList.size > 0) {
            array.add(AttendeesItem(ItemType.SECTION, Pair(absentList.size, VoteStatus.ABSENT)))
            array.addAll(absentList)
        }
        if (unselectedList.size > 0) {
            array.add(AttendeesItem(ItemType.SECTION, Pair(unselectedList.size, VoteStatus.UNSELECTED)))
            array.addAll(unselectedList)
        }

        _items.value = array
    }

    fun onClickCloseButton() {
        _closeEvent.value = Event(true)
    }
}
