package com.sandy.sharedcalendar.repository.remote

import com.sandy.sharedcalendar.data.MemberInfo
import com.sandy.sharedcalendar.data.Test
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerApi {
//    @GET("test")
//    fun getTest(): Call<Test>
//
//    @GET("test/list")
//    fun getTestList(): Call<List<Test>>

    @POST("member/insert")
    fun insertMember(@Body memberInfo: MemberInfo): Single<Response<MemberInfo>>

    @GET("member/select")
    fun getMember(): Single<Response<List<MemberInfo>>>


}