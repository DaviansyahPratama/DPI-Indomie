package com.example.RengginangSabit_DPI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.dashboard.DashboardFragment
import com.example.RengginangSabit_DPI.databinding.ActivityMainBinding
import com.example.RengginangSabit_DPI.monitoring.MonitoringFragment
import com.example.RengginangSabit_DPI.orders.OrdersFragment
import com.example.RengginangSabit_DPI.profile.ProfileFragment
import com.example.RengginangSabit_DPI.reports.ReportsFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Cek keamanan sesi sebelum memuat tampilan utama
        val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        if (!isLogin) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    override fun setupViews() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> DashboardFragment()
                R.id.nav_monitoring -> MonitoringFragment()
                R.id.nav_orders -> OrdersFragment()
                R.id.nav_reports -> ReportsFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> DashboardFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    /**
     * Memuat fragment ke dalam container `fragment_container`
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Fungsi publik untuk mengubah item Bottom Navigation secara terprogram
     */
    fun selectBottomNavItem(itemId: Int) {
        binding.bottomNavigation.selectedItemId = itemId
    }
}
