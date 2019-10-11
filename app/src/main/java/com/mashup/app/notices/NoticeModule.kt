package com.mashup.app.notices

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noticeModule = module {
    viewModel { NoticesViewModel() }
}