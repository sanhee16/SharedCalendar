package com.sandy.sharedcalendar.viewModel

import android.annotation.SuppressLint
import android.util.Log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.sandy.sharedcalendar.remote.KakaoRemote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@SuppressLint("CheckResult")
class SplashViewModel(
) : BaseViewModel() {
    init {
        Completable.timer(2, TimeUnit.SECONDS, Schedulers.io())
            .subscribe { verifyToken() }
    }

    private fun loginSuccess() {
        viewEvent(LOGIN_SUCCESS)
    }
    private fun loginFail() {
        viewEvent(LOGIN_FAIL)
    }

    private fun verifyToken() {
        var disposables = CompositeDisposable()

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.rx.accessTokenInfo() // single (Observable)
                .subscribe({ tokenInfo -> // onSuccess 받음
                    /***
                     * subscribe 함수 : 내가 동작시키기 원하는 것을 사전에 정의해둔 다음 실제 그것이 실행되는 시점 = subscribe이후
                     */

                    Log.v("sandy-TAG", "[1]verifyToken $tokenInfo")
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    loginSuccess()
                    return@subscribe
                }, { error -> // onError 받음
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            // 로그인 필요
                            Log.v("sandy-TAG", "[2]verifyToken $error")
                            loginFail()
                            return@subscribe
                        } else {
                            // 기타 에러
                            Log.v("sandy-TAG", "[3]verifyToken $error")
                            loginFail()
                            return@subscribe
                        }
                    } else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        Log.v("sandy-TAG", "[4]verifyToken")
                        loginSuccess()
                        return@subscribe
                    }
                })
                .addTo(disposables)
        } else {
            //로그인 필요
            Log.v("sandy-TAG", "[6]verifyToken")
            loginFail()
        }
    }
}