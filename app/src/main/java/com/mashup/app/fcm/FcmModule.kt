package com.mashup.app.fcm

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val FcmModule = module {
    viewModel { FcmViewModel(get(), get()) }
}
