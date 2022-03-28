package com.example.sharedcalendar

import android.app.Application
import com.example.sharedcalendar.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel() }
}

// single은 한번만 객체를 생성하고, factory는 호출할 때마다 객체 생성
val repository = module {
}

val util = module {
}
