package com.chart.test.app.data.remote

import com.chart.test.app.data.model.response.PointsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/test/points")
    fun getPoints(@Query("count") count: Int): Observable<PointsResponse>
}
