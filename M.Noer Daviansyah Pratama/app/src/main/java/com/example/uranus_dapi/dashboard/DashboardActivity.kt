package com.example.uranus_dapi.dashboard

import android.content.Intent
import com.example.uranus_dapi.monitoring.MonitoringActivity
import com.example.uranus_dapi.orders.OrdersActivity
import com.example.uranus_dapi.profile.ProfileActivity
import com.example.uranus_dapi.reports.ReportsActivity
import com.example.uranus_dapi.core.BaseActivity
import com.example.uranus_dapi.databinding.ActivityDashboardBinding
import com.example.uranus_dapi.R
class DashboardActivity : BaseActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate) {

    override fun setupViews() {
        // Tandai menu home yang aktif
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_monitoring -> {
                    startActivity(Intent(this, MonitoringActivity::class.java))
                    overridePendingTransition(0, 0) // Hilangkan animasi perpindahan agar mulus
                    finish()
                    true
                }
                R.id.nav_orders -> {
                    startActivity(Intent(this, OrdersActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_reports -> {
                    startActivity(Intent(this, ReportsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}