package com.example.RengginangSabit_DPI.orders

import android.widget.Toast
import com.example.RengginangSabit_DPI.R // Import file resource R
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation // Import fungsi setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityOrdersBinding

class OrdersActivity : BaseActivity<ActivityOrdersBinding>(ActivityOrdersBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar tanpa back button (karena ini tab utama)
        setupToolbar(binding.toolbar, "Daftar Pesanan", showBackButton = false)

        // 2. Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_orders)

        // 3. Event Klik Floating Action Button (+ Tambah Pesanan)
        binding.fabAddOrder.setOnClickListener {
            Toast.makeText(this, "Tambah Pesanan Baru", Toast.LENGTH_SHORT).show()
        }
    }
}