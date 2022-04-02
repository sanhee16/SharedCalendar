package com.sandy.sharedcalendar

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@MainApp)
            modules(viewModelModules, repository, util)
        }
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")

    }
}