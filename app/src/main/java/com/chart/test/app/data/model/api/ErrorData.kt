package com.chart.test.app.data.model.api

data class ErrorData(
    val message: String = "",
    val errorCode: String = "",
    val throwable: Throwable? = null
)
