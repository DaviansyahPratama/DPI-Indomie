package com.example.RengginangSabit_DPI.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.api.SupabaseRepository
import com.example.RengginangSabit_DPI.api.SupabaseUserTableDto
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.DatabaseHelper
import com.example.RengginangSabit_DPI.databinding.ActivityOtpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OtpActivity : BaseActivity<ActivityOtpBinding>(ActivityOtpBinding::inflate) {

    override fun setupViews() {
        val name = intent.getStringExtra(RegisterActivity.EXTRA_NAME) ?: ""
        val phone = intent.getStringExtra(RegisterActivity.EXTRA_PHONE) ?: ""
        val username = intent.getStringExtra(RegisterActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(RegisterActivity.EXTRA_PASSWORD) ?: ""

        binding.btnVerify.setOnClickListener {
            val inputOtp = binding.etOtp.text.toString().trim()

            if (inputOtp.isEmpty() || inputOtp != phone) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP yang Anda masukkan salah atau kosong! Kode OTP harus sama dengan nomor handphone registrasi.")
                    .setPositiveButton("Coba Lagi") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                // 1. Simpan user baru ke tabel `users` di Database SQLite Lokal
                val dbHelper = DatabaseHelper(this)
                dbHelper.registerUser(name, phone, username, password)

                // 2. Sinkronisasi Registrasi ke Supabase (Auth + Tabel public.users)
                val supabaseRepo = SupabaseRepository()
                val userEmail = if (username.contains("@")) username else "$username@rengginangsabit.com"

                // A. Ke Supabase Auth GoTrue
                supabaseRepo.registerUserWithSupabase(
                    email = userEmail,
                    pass = password,
                    name = name,
                    onSuccess = {},
                    onError = {}
                )

                // B. Ke Tabel public.users Supabase
                val publicUserDto = SupabaseUserTableDto(
                    name = name,
                    phone = phone,
                    username = username,
                    password = password
                )
                supabaseRepo.insertUserToPublicTable(publicUserDto, onSuccess = {}, onError = {})

                // 3. Simpan Sesi di SharedPreferences
                val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("KEY_NAME", name)
                    putString("KEY_PHONE", phone)
                    putString("KEY_USERNAME", username)
                    putString("KEY_PASSWORD", password)
                    apply()
                }

                Toast.makeText(this, "Registrasi Berhasil & Tersimpan di Database!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}
