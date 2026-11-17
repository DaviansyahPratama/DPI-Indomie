package com.example.RengginangSabit_DPI.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.DatabaseHelper
import com.example.RengginangSabit_DPI.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun setupViews() {
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            // 1. Ambil input dari etEmail (Username/No.HP) dan etPassword
            val inputUser = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 2. Validasi input kosong
            if (inputUser.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username/Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Cek kredensial ke Database SQLite (Tabel users)
            val dbHelper = DatabaseHelper(this)
            val isDbValid = dbHelper.checkUserLogin(inputUser, password)

            // Fallback untuk akun demo (inputUser == password)
            val isDemoValid = inputUser == password

            if (isDbValid || isDemoValid) {
                // Simpan status isLogin = true ke SharedPreferences
                val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // Pindah ke MainActivity yang menampung Fragment
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username/No. HP atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
