package com.sandy.sharedcalendar.di

import com.sandy.sharedcalendar.repository.remote.RetrofitModule
import com.sandy.sharedcalendar.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel() }
    viewModel { CalendarViewModel() }
    viewModel { CreateRoomViewModel() }
}

// single은 한번만 객체를 생성하고, factory는 호출할 때마다 객체 생성
val repository = module {
//    single { KakaoRemote(androidApplication()) }
}

val util = module {
    single { RetrofitModule }
}
