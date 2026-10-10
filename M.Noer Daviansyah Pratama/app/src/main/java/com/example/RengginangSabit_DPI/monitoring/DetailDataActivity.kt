package com.example.RengginangSabit_DPI.monitoring

import android.content.Context
import android.content.Intent
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityDetailDataBinding

class DetailDataActivity : BaseActivity<ActivityDetailDataBinding>(ActivityDetailDataBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar otomatis mengaktifkan Tombol Kembali
        setupToolbar(binding.toolbar, title = "Lini Produksi A", showBackButton = true)

        // 2. Event Listener untuk Tombol Logout di Toolbar
        binding.btnLogoutToolbar.setOnClickListener {
            logoutUser()
        }
    }

    // Fungsi untuk menghapus sesi login dan berpindah ke LoginActivity
    private fun logoutUser() {
        val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLogin", false) // atau editor.clear()
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}