package com.example.RengginangSabit_DPI.orders

data class Order(
    val id: String,
    val customerName: String,
    val orderDate: String,
    val quantityKg: Int,
    val totalPrice: String,
    val status: String // "Baru", "Diproses", "Selesai"
)
