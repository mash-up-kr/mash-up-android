package com.mashup.app.setting

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SettingModule = module {
    viewModel { SettingViewModel(get()) }
}
