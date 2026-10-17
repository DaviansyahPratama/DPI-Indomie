package com.example.RengginangSabit_DPI.monitoring

import android.content.Intent
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentMonitoringBinding

class MonitoringFragment : BaseFragment<FragmentMonitoringBinding>(FragmentMonitoringBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Monitoring Produksi", showBackButton = false)

        // 2. Navigasi ke Detail Data Activity saat Lini A diklik
        binding.btnLiniA.setOnClickListener {
            val intent = Intent(requireContext(), DetailDataActivity::class.java)
            startActivity(intent)
        }
    }
}
