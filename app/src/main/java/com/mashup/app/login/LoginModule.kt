package com.mashup.app.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val LoginModule = module {
    viewModel { LoginViewModel(get(), get()) }
}
