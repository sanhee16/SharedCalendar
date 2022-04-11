package com.sandy.sharedcalendar.data.response

data class ListResponse<T>(
    val success: Boolean,
    val data: List<T>
)