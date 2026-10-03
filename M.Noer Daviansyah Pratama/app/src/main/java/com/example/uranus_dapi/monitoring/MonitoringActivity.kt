package com.example.uranus_dapi.monitoring

import android.content.Intent
import com.example.uranus_dapi.R
import com.example.uranus_dapi.core.BaseActivity
import com.example.uranus_dapi.core.setupBottomNavigation
import com.example.uranus_dapi.databinding.ActivityMonitoringBinding

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