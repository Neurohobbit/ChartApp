package com.chart.test.app.data.remote

import com.chart.test.app.data.model.api.ErrorData
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ApiObserver<T : Any> constructor(private val compositeDisposable: CompositeDisposable) :
    Observer<T> {

    @Suppress("EmptyFunctionBlock")
    override fun onComplete() {}

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        onError(ErrorData(throwable = e))
    }

    abstract fun onSuccess(data: T)
    abstract fun onError(e: ErrorData)
}
