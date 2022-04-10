package com.sandy.sharedcalendar.remote

import android.app.Activity
import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class KakaoRemote(var context: Context) {
    fun verifyToken(): Boolean {
        var disposables = CompositeDisposable()

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.rx.accessTokenInfo() // single (Observable)
                .subscribe({ tokenInfo -> // onSuccess 받음
                    /***
                     * subscribe 함수 : 내가 동작시키기 원하는 것을 사전에 정의해둔 다음 실제 그것이 실행되는 시점 = subscribe이후
                     */

                    Log.v("sandy-TAG", "[1]verifyToken")
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    return@subscribe
                }, { error -> // onError 받음
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            // 로그인 필요
                            Log.v("sandy-TAG", "[2]verifyToken")
                            return@subscribe
                        } else {
                            // 기타 에러
                            Log.v("sandy-TAG", "[3]verifyToken")
                            return@subscribe
                        }
                    } else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        Log.v("sandy-TAG", "[4]verifyToken")
                        return@subscribe
                    }
                })
                .addTo(disposables)
        } else {
            //로그인 필요
            Log.v("sandy-TAG", "[6]verifyToken")
            return false
        }
        Log.v("sandy-TAG", "verifyToken loopOut")
        return true
    }

    fun login(context: Context) {
        var disposables = CompositeDisposable()

        val a = if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.rx.loginWithKakaoTalk(context)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { error -> // onErrorResumeNext : Exception이 던져진 경우에 대신 스트림을 흐르게 하기 위한 Operator
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    Log.e("sandy-TAG", "[0] login $error")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.i("sandy-TAG", "[1] login")
                        Single.error(error)
                    } else {
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        Log.i("sandy-TAG", "[2] login")
                        UserApiClient.rx.loginWithKakaoAccount(context)
                    }
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    Log.i("sandy-TAG", "[3] login")
                    Log.i("sandy-TAG", "로그인 성공 ${token.accessToken}")

                }, { error ->
                    Log.i("sandy-TAG", "[4] login")
                    Log.e("sandy-TAG", "로그인 실패", error)
                }).addTo(disposables)
            Log.i("sandy-TAG", "[44] login 시도, 버튼 클릭")
        } else {
            UserApiClient.rx.loginWithKakaoAccount(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    Log.i("sandy-TAG", "[5] login")
                    Log.i("sandy-TAG", "로그인 성공 ${token.accessToken}")
                    return@subscribe
                }, { error ->
                    Log.i("sandy-TAG", "[6] login")
                    Log.e("sandy-TAG", "로그인 실패", error)
                    return@subscribe
                }).addTo(disposables)
            Log.i("sandy-TAG", "[66] login")
        }
        Log.i("sandy-TAG", "[final] login $a")
    }

    /**
     * 2022-04-10 13:31:12.290 14053-14053/com.sandy.sharedcalendar I/sandy-TAG: [44] login
    2022-04-10 13:31:12.299 14053-14053/com.sandy.sharedcalendar I/sandy-TAG: [0] login
    2022-04-10 13:31:12.299 14053-14053/com.sandy.sharedcalendar I/sandy-TAG: [2] login
     */

    fun logout() {
        var disposables = CompositeDisposable()

        // 로그아웃
        UserApiClient.rx.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("sandy-TAG", "로그아웃 성공. SDK에서 토큰 삭제 됨")
                return@subscribe
            }, { error ->
                Log.e("sandy-TAG", "로그아웃 실패. SDK에서 토큰 삭제 됨", error)
                return@subscribe
            }).addTo(disposables)
        return
    }
}