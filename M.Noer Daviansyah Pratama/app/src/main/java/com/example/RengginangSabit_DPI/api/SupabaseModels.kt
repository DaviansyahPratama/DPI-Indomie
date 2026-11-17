package com.example.RengginangSabit_DPI.api

// DTO untuk Tabel public.users di Supabase PostgREST
data class SupabaseUserTableDto(
    val id: Long? = null,
    val name: String,
    val phone: String,
    val username: String,
    val password: String
)

// DTO untuk Tabel orders di Supabase PostgREST
data class SupabaseOrderDto(
    val id: Long? = null,
    val order_code: String,
    val customer_name: String,
    val order_date: String,
    val quantity_kg: Int,
    val total_price: String,
    val status: String
)

// DTO untuk Tabel production_lines di Supabase PostgREST
data class SupabaseProductionLineDto(
    val id: Long? = null,
    val line_name: String,
    val status_desc: String,
    val output_rate: String,
    val temperature: String,
    val humidity: String
)

// DTO Request & Response untuk Supabase Auth API
data class SupabaseSignUpRequest(
    val email: String,
    val password: String,
    val data: Map<String, String>? = null
)

data class SupabaseSignInRequest(
    val email: String,
    val password: String
)

data class SupabaseAuthResponse(
    val access_token: String?,
    val token_type: String?,
    val user: SupabaseUser?
)

data class SupabaseUser(
    val id: String?,
    val email: String?
)
