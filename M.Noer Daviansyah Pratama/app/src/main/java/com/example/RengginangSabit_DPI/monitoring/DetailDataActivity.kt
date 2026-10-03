package com.example.RengginangSabit_DPI.monitoring

import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityDetailDataBinding

class DetailDataActivity : BaseActivity<ActivityDetailDataBinding>(ActivityDetailDataBinding::inflate) {

    override fun setupViews() {
        // Setup Toolbar otomatis mengaktifkan Tombol Kembali
        setupToolbar(binding.toolbar, title = "Lini Produksi A", showBackButton = true)
    }
}