package com.example.uranus_dapi.Auth

import android.content.Intent
import com.example.uranus_dapi.dashboard.DashboardActivity
import com.example.uranus_dapi.core.BaseActivity
import com.example.uranus_dapi.databinding.ActivityLoginBinding

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