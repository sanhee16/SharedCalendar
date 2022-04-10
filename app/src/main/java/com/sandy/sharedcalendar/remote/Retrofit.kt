package com.sandy.sharedcalendar.remote

import com.sandy.sharedcalendar.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.HOST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _api = retrofit.create(ServerApi::class.java)
    val api = _api
}