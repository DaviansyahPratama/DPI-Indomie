package com.example.RengginangSabit_DPI.dashboard

import android.content.Intent
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityDashboardBinding
import com.example.RengginangSabit_DPI.profile.ProfileActivity

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 2. Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_home)

        // 3. Profile Button Click
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }
}