package com.example.RengginangSabit_DPI.Auth

import android.content.Intent
import com.example.RengginangSabit_DPI.dashboard.DashboardActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun setupViews() {
        binding.btnLogin.setOnClickListener {
            // Setelah login berhasil, pindah ke DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}