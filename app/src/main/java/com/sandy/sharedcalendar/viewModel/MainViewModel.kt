package com.sandy.sharedcalendar.viewModel

import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainViewModel(
) : BaseViewModel() {
    init {
    }

    fun onClickCreateRoom() {
        viewEvent(START_ACTIVITY_CREATE_ROOM)
    }

    fun logout() {
        var disposables = CompositeDisposable()

        // 로그아웃
        UserApiClient.rx.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("sandy-TAG", "로그아웃 성공. SDK에서 토큰 삭제 됨")
                viewEvent(LOGOUT_SUCCESS)
                return@subscribe
            }, { error ->
                Log.e("sandy-TAG", "로그아웃 실패. SDK에서 토큰 삭제 됨", error)
                viewEvent(LOGOUT_FAIL)
                return@subscribe
            }).addTo(disposables)
    }
}