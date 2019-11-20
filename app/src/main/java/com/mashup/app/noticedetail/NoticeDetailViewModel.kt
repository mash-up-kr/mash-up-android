package com.mashup.app.noticedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.model.Notice

class NoticeDetailViewModel(
    notice: Notice
) : ViewModel() {

    private val _item = MutableLiveData<Notice>()
    val item: LiveData<Notice> = _item

    init {
        _item.value = notice
    }

}
