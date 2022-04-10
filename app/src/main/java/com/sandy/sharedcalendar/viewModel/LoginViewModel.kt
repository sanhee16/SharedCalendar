package com.sandy.sharedcalendar.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.sandy.sharedcalendar.remote.KakaoRemote
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class LoginViewModel(
) : BaseViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    init {

    }
    fun setContext(context: Context) {
        this.context = context
    }

    fun onClickKaKaoLogin() {
        var disposables = CompositeDisposable()

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.rx.loginWithKakaoTalk(context)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { error -> // onErrorResumeNext : Exception이 던져진 경우에 대신 스트림을 흐르게 하기 위한 Operator
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    Log.e("sandy-TAG", "[0] login $error")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.i("sandy-TAG", "[1] login")
                        viewEvent(LOGIN_FAIL)
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
                    viewEvent(LOGIN_SUCCESS)
                    return@subscribe
                }, { error ->
                    Log.i("sandy-TAG", "[4] login")
                    Log.e("sandy-TAG", "로그인 실패", error)
                    viewEvent(LOGIN_FAIL)
                    return@subscribe
                }).addTo(disposables)
            Log.i("sandy-TAG", "[44] login 시도, 버튼 클릭")
        } else {
            UserApiClient.rx.loginWithKakaoAccount(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    Log.i("sandy-TAG", "[5] login")
                    Log.i("sandy-TAG", "로그인 성공 ${token.accessToken}")
                    viewEvent(LOGIN_SUCCESS)
                    return@subscribe
                }, { error ->
                    Log.i("sandy-TAG", "[6] login")
                    Log.e("sandy-TAG", "로그인 실패", error)
                    viewEvent(LOGIN_FAIL)
                    return@subscribe
                }).addTo(disposables)
            Log.i("sandy-TAG", "[66] login")
        }
    }
}