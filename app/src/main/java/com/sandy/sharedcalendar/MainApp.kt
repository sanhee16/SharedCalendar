package com.sandy.sharedcalendar

import android.app.Application
import android.util.Log
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import com.sandy.sharedcalendar.di.repository
import com.sandy.sharedcalendar.di.util
import com.sandy.sharedcalendar.di.viewModelModules
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.IOException
import java.net.SocketException


class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@MainApp)
            modules(viewModelModules, repository, util)
        }
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        // undelivered error hooking
//        RxJavaPlugins.setErrorHandler { e ->
//            if (e is UndeliverableException) {
//                e.also { it.cause }
//            }
//            if (e is IOException || e is SocketException) {
//                // fine, irrelevant network problem or API that throws on cancellation
//                return@setErrorHandler
//            }
//            if (e is InterruptedException) {
//                // fine, some blocking code was interrupted by a dispose call
//                return@setErrorHandler
//            }
//            if (e is NullPointerException || e is IllegalArgumentException) {
//                // that's likely a bug in the application
//                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
//                return@setErrorHandler
//            }
//            if (e is IllegalStateException) {
//                // that's a bug in RxJava or in a custom operator
//                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
//                return@setErrorHandler
//            }
//            Log.w("Undeliverable exception", "$e")
//        }
    }
}