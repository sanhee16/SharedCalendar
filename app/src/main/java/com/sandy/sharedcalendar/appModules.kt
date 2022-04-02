package com.sandy.sharedcalendar

import com.sandy.sharedcalendar.viewModel.CalendarViewModel
import com.sandy.sharedcalendar.viewModel.CreateRoomViewModel
import com.sandy.sharedcalendar.viewModel.LoginViewModel
import com.sandy.sharedcalendar.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { LoginViewModel() }
    viewModel { CalendarViewModel() }
    viewModel { CreateRoomViewModel() }
}

// single은 한번만 객체를 생성하고, factory는 호출할 때마다 객체 생성
val repository = module {
}

val util = module {
}
