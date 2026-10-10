package com.example.RengginangSabit_DPI.dashboard

import android.content.Context
import android.content.Intent
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityDashboardBinding
import com.example.RengginangSabit_DPI.profile.ProfileActivity

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate) {

    override fun setupViews() {
        // 1. Cek keamanan: Jika belum login, tendang balik ke LoginActivity
        val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        if (!isLogin) {
            navigateToLogin()
            return
        }

        // 2. Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 3. Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_home)

        // 4. Profile Button Click
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        // 5. Setup Listener Tombol Logout (Toolbar & Tombol Bawah)
        binding.btnLogoutToolbar.setOnClickListener {
            logoutUser()
        }

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    // Fungsi untuk menghapus sesi dan kembali ke LoginActivity
    fun logoutUser() {
        val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLogin", false)
        editor.apply()

        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}