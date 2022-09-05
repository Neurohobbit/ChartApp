package com.chart.test.app.ui.chart

import com.chart.test.app.data.RepositoryImpl
import com.chart.test.app.ui.base.BaseViewModel
import com.chart.test.app.ui.model.PointsPairs
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ChartViewModel @Inject constructor(private val repository: RepositoryImpl) : BaseViewModel() {

    private val pointsPairsSubject: BehaviorSubject<PointsPairs> =
        BehaviorSubject.createDefault(PointsPairs(emptyList()))
    val pointsPairs: Observable<PointsPairs>
        get() = pointsPairsSubject.hide()

    init {
        val pairs = repository
            .getPointsPairs()
        pairs?.let {
            pointsPairsSubject.onNext(it)
        }
    }
}
