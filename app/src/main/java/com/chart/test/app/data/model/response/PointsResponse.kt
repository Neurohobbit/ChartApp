package com.chart.test.app.data.model.response

import com.chart.test.app.ui.model.PointsPairs
import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("points")
    val points: List<PointsPair>
)

data class PointsPair(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)

fun PointsResponse.toPointsPairModel(): PointsPairs {
    val list = points.sortedBy { it.x }.map { Pair(it.x, it.y) }
    return PointsPairs(list = list)
}
