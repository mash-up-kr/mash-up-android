package com.mashup.app.attendees

import com.mashup.model.NoticeAttendance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AttendeesModule = module {
    viewModel { (attendanceList: List<NoticeAttendance>) -> AttendeesViewModel(attendanceList) }
}