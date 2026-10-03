package com.example.RengginangSabit_DPI.dashboard

import android.content.Intent
import com.example.RengginangSabit_DPI.monitoring.MonitoringActivity
import com.example.RengginangSabit_DPI.orders.OrdersActivity
import com.example.RengginangSabit_DPI.profile.ProfileActivity
import com.example.RengginangSabit_DPI.reports.ReportsActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityDashboardBinding
import com.example.RengginangSabit_DPI.R
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