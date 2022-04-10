package com.sandy.sharedcalendar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandy.sharedcalendar.Event

open class BaseViewModel : ViewModel() {
    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>> get() = _viewEvent

    companion object {
        const val SHOW_PROGRESS_BAR = "SHOW_PROGRESS_BAR"
        const val HIDE_PROGRESS_BAR = "HIDE_PROGRESS_BAR"

        const val START_ACTIVITY_CREATE_ROOM = "START_ACTIVITY_MAKE_ROOM"

        const val REQUEST_LOGIN = "REQUEST_LOGIN"
        const val LOGIN_SUCCESS = "LOGIN_SUCCESS"
        const val LOGIN_FAIL = "LOGIN_FAIL"
        const val LOGOUT_SUCCESS = "LOGOUT_SUCCESS"
        const val LOGOUT_FAIL = "LOGOUT_FAIL"
    }

    fun viewEvent(content: Any) {
        _viewEvent.postValue(Event(content))
    }

    fun showProgressBar() = viewEvent(SHOW_PROGRESS_BAR)
    fun hideProgressBar() = viewEvent(HIDE_PROGRESS_BAR)
}