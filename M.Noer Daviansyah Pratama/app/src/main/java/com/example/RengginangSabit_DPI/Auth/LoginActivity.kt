package com.example.RengginangSabit_DPI.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun setupViews() {
        binding.btnLogin.setOnClickListener {
            // 1. Ambil input dari etEmail dan etPassword
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 2. Validasi input kosong
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Pengecekan: Isi email dan password disamakan
            if (email == password) {
                // Simpan status isLogin = true ke SharedPreferences
                val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                // Pindah ke MainActivity yang menampung Fragment
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Password harus sama dengan Email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
