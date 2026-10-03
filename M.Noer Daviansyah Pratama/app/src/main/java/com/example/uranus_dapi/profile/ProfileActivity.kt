package com.example.uranus_dapi.profile

import android.content.Intent
import android.widget.Toast
import com.example.uranus_dapi.Auth.LoginActivity
import com.example.uranus_dapi.R
import com.example.uranus_dapi.core.BaseActivity
import com.example.uranus_dapi.core.setupBottomNavigation
import com.example.uranus_dapi.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate) {

    override fun setupViews() {
        // Setup Toolbar
        setupToolbar(binding.toolbar, "Profil Saya", showBackButton = false)

        // Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_profile)

        // Event Klik Keluar Aplikasi
        binding.tvLogout.setOnClickListener {
            Toast.makeText(this, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}