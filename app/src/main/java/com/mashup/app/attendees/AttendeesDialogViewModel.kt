package com.mashup.app.attendees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.model.NoticeAttendance

class AttendeesDialogViewModel(
    attendanceList: List<NoticeAttendance>
) : ViewModel() {
    private val _items = MutableLiveData<List<NoticeAttendance>>().apply { value = emptyList() }
    val items: LiveData<List<NoticeAttendance>> = _items

    init {
        _items.value = attendanceList
    }
}