package com.sandy.sharedcalendar.remote

import com.sandy.sharedcalendar.data.Test
import retrofit2.Call
import retrofit2.http.GET

interface ServerApi {
    @GET("test")
    fun getTest(): Call<Test>
    @GET("test/list")
    fun getTestList(): Call<List<Test>>
}