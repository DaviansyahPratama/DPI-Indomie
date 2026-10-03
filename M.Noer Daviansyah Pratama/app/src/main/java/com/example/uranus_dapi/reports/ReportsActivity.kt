package com.example.uranus_dapi.reports

import android.widget.Toast
import com.example.uranus_dapi.core.BaseActivity

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