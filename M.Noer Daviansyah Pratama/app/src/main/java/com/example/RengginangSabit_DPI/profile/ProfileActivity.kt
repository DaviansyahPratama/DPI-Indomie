package com.example.RengginangSabit_DPI.profile

import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Profil Saya", showBackButton = false)

        // 2. Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_profile)

        // 3. Event Klik Buka Website Vercel Rengginang Sabit
        binding.tvWebsite.setOnClickListener {
            openWebPage("https://rengginangsabit.vercel.app")
        }

        // 4. Event Klik Keluar Aplikasi
        binding.tvLogout.setOnClickListener {
            Toast.makeText(this, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    // Fungsi Helper untuk membuka Website di WebViewActivity
    private fun openWebPage(url: String) {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra("EXTRA_URL", url)
        }
        startActivity(intent)
    }
}