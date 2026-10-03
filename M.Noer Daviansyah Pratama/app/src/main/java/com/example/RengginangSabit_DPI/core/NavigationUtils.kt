package com.example.RengginangSabit_DPI.core

import android.content.Intent
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.dashboard.DashboardActivity
import com.example.RengginangSabit_DPI.monitoring.MonitoringActivity
import com.example.RengginangSabit_DPI.orders.OrdersActivity
import com.example.RengginangSabit_DPI.profile.ProfileActivity
import com.example.RengginangSabit_DPI.reports.ReportsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Extension function untuk menghubungkan BottomNavigationView di setiap Activity
 */
fun BaseActivity<*>.setupBottomNavigation(bottomNav: BottomNavigationView, activeItemId: Int) {
    // Set item yang sedang aktif sesuai halaman saat ini
    bottomNav.selectedItemId = activeItemId

    bottomNav.setOnItemSelectedListener { item ->
        // Jika mengklik menu yang sedang aktif, tidak perlu pindah halaman
        if (item.itemId == activeItemId) return@setOnItemSelectedListener true

        val intent = when (item.itemId) {
            R.id.nav_home -> Intent(this, DashboardActivity::class.java)
            R.id.nav_monitoring -> Intent(this, MonitoringActivity::class.java)
            R.id.nav_orders -> Intent(this, OrdersActivity::class.java)
            R.id.nav_reports -> Intent(this, ReportsActivity::class.java)
            R.id.nav_profile -> Intent(this, ProfileActivity::class.java)
            else -> null
        }

        intent?.let {
            startActivity(it)
            // Menghilangkan animasi transisi bawaan Activity agar perpindahan terasa seperti tab/fragment
            overridePendingTransition(0, 0)
            finish()
        }
        true
    }
}