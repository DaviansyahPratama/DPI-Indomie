package com.example.RengginangSabit_DPI.monitoring

data class ProductionLine(
    val dbId: Long = 0,
    val lineName: String,
    val statusDesc: String,
    val outputRate: String,
    val temperature: String,
    val humidity: String
)
