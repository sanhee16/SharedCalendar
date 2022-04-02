package com.sandy.sharedcalendar.viewModel

class MainViewModel : BaseViewModel() {


    init {
    }

    fun onClickCreateRoom() {
        viewEvent(START_ACTIVITY_CREATE_ROOM)
    }
}