package com.chart.test.app.data

import com.chart.test.app.data.model.response.PointsResponse
import com.chart.test.app.ui.model.PointsPairs
import io.reactivex.Observable

interface Repository {

    fun getPoints(count: Int): Observable<PointsResponse>

    fun setPointsPairs(pairs: PointsPairs)
    fun getPointsPairs(): PointsPairs?
}
