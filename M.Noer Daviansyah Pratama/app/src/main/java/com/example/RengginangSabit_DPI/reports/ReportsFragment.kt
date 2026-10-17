package com.example.RengginangSabit_DPI.reports

import android.widget.Toast
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentReportsBinding

class ReportsFragment : BaseFragment<FragmentReportsBinding>(FragmentReportsBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Laporan Produksi", showBackButton = false)

        // 2. Event Tombol Export Laporan
        binding.btnDownloadPdf.setOnClickListener {
            Toast.makeText(requireContext(), "Mengunduh Laporan PDF...", Toast.LENGTH_SHORT).show()
        }

        binding.btnDownloadExcel.setOnClickListener {
            Toast.makeText(requireContext(), "Mengunduh Laporan Excel...", Toast.LENGTH_SHORT).show()
        }
    }
}
