package com.chart.test.app.ui.search

import com.chart.test.app.data.Repository
import com.chart.test.app.data.model.api.ErrorData
import com.chart.test.app.data.model.response.PointsResponse
import com.chart.test.app.data.model.response.toPointsPairModel
import com.chart.test.app.data.remote.ApiObserver
import com.chart.test.app.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val requestInProgressSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    val requestInProgress: Observable<Boolean>
        get() = requestInProgressSubject.hide()

    private val resultSuccessSubject: PublishSubject<Unit> = PublishSubject.create()
    val resultSuccess: Observable<Unit>
        get() = resultSuccessSubject.hide()

    private val resultErrorSubject: PublishSubject<String> = PublishSubject.create()
    val resultError: Observable<String>
        get() = resultErrorSubject.hide()

    internal fun getPoints(count: Int) {
        requestInProgressSubject.onNext(true)
        repository.getPoints(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<PointsResponse>(compositeDisposable) {
                override fun onSuccess(data: PointsResponse) {
                    repository.setPointsPairs(data.toPointsPairModel())
                    requestInProgressSubject.onNext(false)
                    resultSuccessSubject.onNext(Unit)
                }

                override fun onError(e: ErrorData) {
                    requestInProgressSubject.onNext(false)
                    val code: Int = (e.throwable as HttpException).code()
                    resultErrorSubject.onNext(
                        when (code) {
                            400 -> "Bad request"
                            500 -> "Server error"
                            else -> e.message
                        }
                    )
                }
            })
    }
}
