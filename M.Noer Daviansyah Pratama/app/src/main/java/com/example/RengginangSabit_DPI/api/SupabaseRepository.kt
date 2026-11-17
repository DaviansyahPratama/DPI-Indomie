package com.example.RengginangSabit_DPI.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository untuk mengelola panggilan API REST Supabase (Auth, Users, Orders, Production Lines)
 */
class SupabaseRepository {

    private val api = SupabaseClient.apiService

    // =============================
    // 1. SUPABASE AUTH INTEGRATION
    // =============================

    fun registerUserWithSupabase(
        email: String,
        pass: String,
        name: String,
        onSuccess: (SupabaseAuthResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = SupabaseSignUpRequest(
            email = email,
            password = pass,
            data = mapOf("full_name" to name)
        )
        api.signUpUser(request).enqueue(object : Callback<SupabaseAuthResponse> {
            override fun onResponse(call: Call<SupabaseAuthResponse>, response: Response<SupabaseAuthResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError("Daftar Supabase Gagal (${response.code()}): ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SupabaseAuthResponse>, t: Throwable) {
                onError("Koneksi Supabase Gagal: ${t.localizedMessage}")
            }
        })
    }

    fun insertUserToPublicTable(
        user: SupabaseUserTableDto,
        onSuccess: (List<SupabaseUserTableDto>?) -> Unit,
        onError: (String) -> Unit
    ) {
        api.createPublicUser(user).enqueue(object : Callback<List<SupabaseUserTableDto>> {
            override fun onResponse(
                call: Call<List<SupabaseUserTableDto>>,
                response: Response<List<SupabaseUserTableDto>>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError("Gagal simpan ke tabel users public (${response.code()})")
                }
            }

            override fun onFailure(call: Call<List<SupabaseUserTableDto>>, t: Throwable) {
                onError("Gagal koneksi ke Supabase: ${t.localizedMessage}")
            }
        })
    }

    // =============================
    // 2. ORDERS CRUD SUPABASE API
    // =============================

    fun fetchOrdersFromSupabase(
        onSuccess: (List<SupabaseOrderDto>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getOrders().enqueue(object : Callback<List<SupabaseOrderDto>> {
            override fun onResponse(call: Call<List<SupabaseOrderDto>>, response: Response<List<SupabaseOrderDto>>) {
                if (response.isSuccessful) {
                    onSuccess(response.body() ?: emptyList())
                } else {
                    onError("Gagal mengambil data pesanan dari Supabase: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SupabaseOrderDto>>, t: Throwable) {
                onError("Gagal terhubung ke Supabase API: ${t.localizedMessage}")
            }
        })
    }

    fun createOrderInSupabase(
        order: SupabaseOrderDto,
        onSuccess: (List<SupabaseOrderDto>?) -> Unit,
        onError: (String) -> Unit
    ) {
        api.createOrder(order).enqueue(object : Callback<List<SupabaseOrderDto>> {
            override fun onResponse(call: Call<List<SupabaseOrderDto>>, response: Response<List<SupabaseOrderDto>>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError("Gagal menambah pesanan di Supabase: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SupabaseOrderDto>>, t: Throwable) {
                onError("Gagal terhubung ke Supabase: ${t.localizedMessage}")
            }
        })
    }

    fun deleteOrderFromSupabase(
        id: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.deleteOrder("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Gagal menghapus pesanan dari Supabase")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError("Error koneksi: ${t.localizedMessage}")
            }
        })
    }

    // ===================================
    // 3. PRODUCTION LINES CRUD SUPABASE
    // ===================================

    fun fetchProductionLinesFromSupabase(
        onSuccess: (List<SupabaseProductionLineDto>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getProductionLines().enqueue(object : Callback<List<SupabaseProductionLineDto>> {
            override fun onResponse(
                call: Call<List<SupabaseProductionLineDto>>,
                response: Response<List<SupabaseProductionLineDto>>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body() ?: emptyList())
                } else {
                    onError("Gagal mengambil data lini produksi dari Supabase: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SupabaseProductionLineDto>>, t: Throwable) {
                onError("Error koneksi ke Supabase: ${t.localizedMessage}")
            }
        })
    }
}
