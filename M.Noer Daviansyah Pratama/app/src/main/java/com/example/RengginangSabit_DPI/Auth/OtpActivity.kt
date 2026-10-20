package com.example.RengginangSabit_DPI.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityOtpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OtpActivity : BaseActivity<ActivityOtpBinding>(ActivityOtpBinding::inflate) {

    override fun setupViews() {
        // PERBAIKAN: Gunakan RegisterActivity.EXTRA_* untuk mengambil data Intent
        val name = intent.getStringExtra(RegisterActivity.EXTRA_NAME) ?: ""
        val phone = intent.getStringExtra(RegisterActivity.EXTRA_PHONE) ?: ""
        val username = intent.getStringExtra(RegisterActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(RegisterActivity.EXTRA_PASSWORD) ?: ""

        binding.btnVerify.setOnClickListener {
            val inputOtp = binding.etOtp.text.toString().trim()

            // 1. Cek jika OTP kosong atau TIDAK sama dengan No. Handphone[cite: 3]
            if (inputOtp.isEmpty() || inputOtp != phone) {
                // Tampilkan Error Alert Dialog[cite: 3]
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP yang Anda masukkan salah atau kosong! Kode OTP harus sama dengan nomor handphone registrasi.")
                    .setPositiveButton("Coba Lagi") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {

                val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("KEY_NAME", name)
                    putString("KEY_PHONE", phone)
                    putString("KEY_USERNAME", username)
                    putString("KEY_PASSWORD", password)
                    apply()
                }

                Toast.makeText(this, "Registrasi & Verifikasi Berhasil!", Toast.LENGTH_SHORT).show()

                // Lanjut balik ke Halaman Login[cite: 3]
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}