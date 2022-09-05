package com.chart.test.app.data

import com.chart.test.app.data.remote.ApiService
import com.chart.test.app.ui.model.PointsPairs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: ApiService
) : Repository {

    private var pointsPairs: PointsPairs? = null

    override fun getPoints(count: Int) = api.getPoints(count)

    override fun setPointsPairs(pairs: PointsPairs) {
        pointsPairs = pairs
    }

    override fun getPointsPairs(): PointsPairs? =
        pointsPairs
}
