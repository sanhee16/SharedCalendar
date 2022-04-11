package com.sandy.sharedcalendar.repository.remote

import com.sandy.sharedcalendar.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//https://square.github.io/retrofit/
object RetrofitModule {

    //이 함수를 통해 로그를 자세히 볼수있다
    private fun createClientAuth(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.addNetworkInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )//로그 전부 찍음
        return okHttpClientBuilder.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.HOST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClientAuth())
        .build()

    private val _api = retrofit.create(ServerApi::class.java)
    val api = _api
}