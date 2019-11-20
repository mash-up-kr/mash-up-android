package com.mashup.app.attendees

import com.mashup.model.NoticeAttendance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AttendeesDialogModule = module {
    viewModel { (attendanceList: List<NoticeAttendance>) -> AttendeesDialogViewModel(attendanceList) }
}