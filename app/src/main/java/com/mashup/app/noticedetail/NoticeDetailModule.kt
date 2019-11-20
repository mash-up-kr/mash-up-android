package com.mashup.app.noticedetail

import com.mashup.model.Notice
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val NoticeDetailModule = module {
    viewModel { (notice: Notice) -> NoticeDetailViewModel(notice) }
}
