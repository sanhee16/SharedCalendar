package com.sandy.sharedcalendar.data.response


data class DataResponse<T>(
    val success: Boolean,
    val data: T
)