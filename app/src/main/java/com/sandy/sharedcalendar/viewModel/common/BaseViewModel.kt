package com.sandy.sharedcalendar.viewModel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

open class BaseViewModel : ViewModel() {
    private val _viewEvent = MutableLiveData<Event<Any>>()
    private val _onError = MutableLiveData<Throwable>()

    protected val compositeDisposable = CompositeDisposable()

    val viewEvent: LiveData<Event<Any>> get() = _viewEvent
    val onError: LiveData<Throwable> get() = _onError

    companion object {
        const val SHOW_PROGRESS_BAR = "SHOW_PROGRESS_BAR"
        const val HIDE_PROGRESS_BAR = "HIDE_PROGRESS_BAR"

        const val START_ACTIVITY_CREATE_ROOM = "START_ACTIVITY_MAKE_ROOM"

        const val REQUEST_LOGIN = "REQUEST_LOGIN"
        const val LOGIN_SUCCESS = "LOGIN_SUCCESS"
        const val LOGIN_FAIL = "LOGIN_FAIL"
        const val LOGOUT_SUCCESS = "LOGOUT_SUCCESS"
        const val LOGOUT_FAIL = "LOGOUT_FAIL"
    }

    fun viewEvent(content: Any) {
        _viewEvent.postValue(Event(content))
    }

    fun showProgressBar() = viewEvent(SHOW_PROGRESS_BAR)
    fun hideProgressBar() = viewEvent(HIDE_PROGRESS_BAR)
    fun onProgress(isShow: Boolean) {
//        if (isShow)
//            viewEvent(SHOW_PROGRESS_BAR)
//        else
//            viewEvent(HIDE_PROGRESS_BAR)
    }


    /**
     * 쌓인 compositDisposable을 clear
     *
     * Activity/Fragment의 onDestroy 타이밍에 리소스 정리
     * dispose() 호출 시 compositeDisposable 재사용이 불가능하므로 clear()만 수행
     */
    fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

    /**
     * ViewModel이 제거될 때 호출되는 onCleared()
     *
     * compositeDisposable을 재사용할 일이 없으므로 dispose() 수행
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun addDisposable(disposable: Disposable): Disposable {
        compositeDisposable.add(disposable)
        return disposable
    }

    fun <T> runDisposable(disposable: Observable<T>, callback: (result: T) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, this::onError)
        )
    }

    fun <T> runDisposable(disposable: Observable<T>, callback: (result: T) -> Unit, error: (error: Throwable) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, {
                    this.onError(it)
                    error(it)
                })
        )
    }

    fun <T> runDisposable(disposable: Single<T>, callback: (result: T) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, this::onError)
        )
    }

    fun <T> runDisposable(disposable: Single<T>, callback: (result: T) -> Unit, error: (error: Throwable) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, {
                    this.onError(it)
                    error(it)
                })
        )
    }

    fun <T> runDisposable(disposable: Maybe<T>, callback: (result: T) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, this::onError)
        )
    }

    fun <T> runDisposable(disposable: Maybe<T>, callback: (result: T) -> Unit, error: (error: Throwable) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback(it)
                    onProgress(false)
                }, {
                    this.onError(it)
                    error(it)
                })
        )
    }

    fun runDisposable(disposable: Completable, callback: () -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback()
                    onProgress(false)
                }, this::onError)
        )
    }

    fun runDisposable(disposable: Completable, callback: () -> Unit, error: (error: Throwable) -> Unit): Disposable {
        onProgress(true)
        return addDisposable(
            disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback()
                    onProgress(false)
                }, {
                    this.onError(it)
                    error(it)
                })
        )
    }

    protected open fun onError(error: Throwable) {
        error.printStackTrace()
        _onError.postValue(error)
        onProgress(false)
    }
}