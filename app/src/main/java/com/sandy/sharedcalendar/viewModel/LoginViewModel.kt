package com.sandy.sharedcalendar.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.sandy.sharedcalendar.data.MemberInfo
import com.sandy.sharedcalendar.repository.remote.RetrofitModule
import com.sandy.sharedcalendar.repository.remote.ServerApi
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import org.koin.core.module.Module
import retrofit2.Call
import retrofit2.HttpException
import java.lang.Error
import kotlin.reflect.typeOf


class LoginViewModel(
    private val retrofitModule: RetrofitModule
//    private val serverApi: ServerApi
) : BaseViewModel() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context

    init {

    }

    fun setContext(context: Context) {
        this.context = context
    }

    @SuppressLint("CheckResult")
    fun onClickKaKaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // [1] 카카오톡 로그인 가능
            runDisposable(
                UserApiClient.rx.loginWithKakaoTalk(context) // 1차 스틀미
                    .onErrorResumeNext { error -> // onErrorResumeNext : Exception이 던져진 경우에 대신 스트림을 흐르게 하기 위한 Operator
                        // map에서 Exception이 throw 되면 onErrorResumeNext가 호출되어 대신할 스트림으로 아래가 실행됨.
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        Log.e("sandy-TAG", "[0] login $error")
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.i("sandy-TAG", "[1] login")
                            viewEvent(LOGIN_FAIL)
                            Single.error(error) // 사용자가 취소했으면 에러 반환
                        } else {
                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            Log.i("sandy-TAG", "[2] login")
//                        Single.error(error) // 사용자가 취소했으면 에러 반환
//                        UserApiClient.rx.loginWithKakaoAccount(context) // 카카오톡 연결된 계정없으면 카카오계정으로 로그인시도하는 스트림 반환
                            Single.error(error) // 사용자가 취소했으면 에러 반환
                        }
                    }, { token ->
                    Log.i("sandy-TAG", "[카카오톡으로] login 성공 : token = ${token.accessToken}")
                    updateMember()
                }, { error ->// 카카오 계정으로 로그인 시도했거나, 카카오톡으로 로그인 시도했는데 실패!
                    Log.i("sandy-TAG", "[카카오톡으로] login 실패 : err = $error")
                    viewEvent(LOGIN_FAIL)
                    return@runDisposable
                }
            )
        } else {
            // [2] 카카오 계정으로 로그인 시도
            runDisposable(
                UserApiClient.rx.loginWithKakaoAccount(context),
                { token ->
                    Log.i("sandy-TAG", "[카카오계정으로] login 성공 : token = ${token.accessToken}")
                    Log.i("sandy-TAG", "로그인 성공 ${token.accessToken}")
                    updateMember()
                }, { error ->// 카카오 계정으로 로그인 시도했거나, 카카오톡으로 로그인 시도했는데 실패!
                    Log.e("sandy-TAG", "[카카오계정으로] login 실패 : err = $error")
                    viewEvent(LOGIN_FAIL)
                    return@runDisposable
                }
            )
        }
    }

    private fun updateMember() {
        runDisposable(
            UserApiClient.rx.me()
                .flatMap { user ->
                    Log.v("sandy-TAG","id : ${user.id}")
                    retrofitModule.api.insertMember(
                        MemberInfo(
                            user.kakaoAccount?.name,
                            user.kakaoAccount?.email,
                            user.id
                        )
                    )
                }.map { t -> if (t.isSuccessful) t else throw HttpException(t) },
            { res ->
                Log.v("sandy-TAG", "insert success : ${res.body()}")
                viewEvent(LOGIN_SUCCESS)
            },
            { err ->
                Log.e("sandy-TAG", "insert fail : $err")
                viewEvent(LOGIN_FAIL)
            }
        )
    }
}
