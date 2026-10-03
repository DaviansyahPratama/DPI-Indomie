package com.example.RengginangSabit_DPI.monitoring

import android.content.Intent
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityMonitoringBinding

class MonitoringActivity : BaseActivity<ActivityMonitoringBinding>(ActivityMonitoringBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Monitoring Produksi", showBackButton = false)

        // 2. Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_monitoring)

        // 3. Navigasi ke Detail Data Activity saat Lini A diklik
        binding.btnLiniA.setOnClickListener {
            val intent = Intent(this, DetailDataActivity::class.java)
            startActivity(intent)
        }
    }
}