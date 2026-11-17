package com.example.RengginangSabit_DPI.api

import retrofit2.Call
import retrofit2.http.*

interface SupabaseApiService {

    // =====================================
    // 1. SUPABASE AUTH GO-TRUE ENDPOINTS
    // =====================================

    @POST("auth/v1/signup")
    fun signUpUser(
        @Body request: SupabaseSignUpRequest
    ): Call<SupabaseAuthResponse>

    @POST("auth/v1/token?grant_type=password")
    fun signInUser(
        @Body request: SupabaseSignInRequest
    ): Call<SupabaseAuthResponse>

    // =====================================
    // 2. PUBLIC USERS TABLE ENDPOINTS
    // =====================================

    @GET("rest/v1/users?select=*")
    fun getUsers(): Call<List<SupabaseUserTableDto>>

    @POST("rest/v1/users")
    fun createPublicUser(
        @Body user: SupabaseUserTableDto
    ): Call<List<SupabaseUserTableDto>>

    // =====================================
    // 3. ORDERS TABLE ENDPOINTS (PostgREST)
    // =====================================

    @GET("rest/v1/orders?select=*")
    fun getOrders(): Call<List<SupabaseOrderDto>>

    @POST("rest/v1/orders")
    fun createOrder(
        @Body order: SupabaseOrderDto
    ): Call<List<SupabaseOrderDto>>

    @PATCH("rest/v1/orders")
    fun updateOrder(
        @Query("id") filterId: String, // format eq.1
        @Body order: SupabaseOrderDto
    ): Call<List<SupabaseOrderDto>>

    @DELETE("rest/v1/orders")
    fun deleteOrder(
        @Query("id") filterId: String // format eq.1
    ): Call<Void>

    // =====================================
    // 4. PRODUCTION_LINES TABLE ENDPOINTS
    // =====================================

    @GET("rest/v1/production_lines?select=*")
    fun getProductionLines(): Call<List<SupabaseProductionLineDto>>

    @POST("rest/v1/production_lines")
    fun createProductionLine(
        @Body line: SupabaseProductionLineDto
    ): Call<List<SupabaseProductionLineDto>>

    @PATCH("rest/v1/production_lines")
    fun updateProductionLine(
        @Query("id") filterId: String, // format eq.1
        @Body line: SupabaseProductionLineDto
    ): Call<List<SupabaseProductionLineDto>>

    @DELETE("rest/v1/production_lines")
    fun deleteProductionLine(
        @Query("id") filterId: String // format eq.1
    ): Call<Void>
}
