package com.example.RengginangSabit_DPI.reports

import android.widget.Toast
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.core.setupBottomNavigation
import com.example.RengginangSabit_DPI.databinding.ActivityReportsBinding

class ReportsActivity : BaseActivity<ActivityReportsBinding>(ActivityReportsBinding::inflate) {

    override fun setupViews() {
        // Setup Toolbar
        setupToolbar(binding.toolbar, "Laporan Produksi", showBackButton = false)

        // Setup Bottom Navigation
        setupBottomNavigation(binding.bottomNavigation, R.id.nav_reports)

        // Event Tombol Export Laporan
        binding.btnDownloadPdf?.setOnClickListener {
            Toast.makeText(this, "Mengunduh Laporan PDF...", Toast.LENGTH_SHORT).show()
        }

        binding.btnDownloadExcel?.setOnClickListener {
            Toast.makeText(this, "Mengunduh Laporan Excel...", Toast.LENGTH_SHORT).show()
        }
    }
}