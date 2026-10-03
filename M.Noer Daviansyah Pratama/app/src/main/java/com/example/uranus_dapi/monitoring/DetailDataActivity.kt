package com.example.uranus_dapi.monitoring

import com.example.uranus_dapi.core.BaseActivity
import com.example.uranus_dapi.databinding.ActivityDetailDataBinding

class DetailDataActivity : BaseActivity<ActivityDetailDataBinding>(ActivityDetailDataBinding::inflate) {

    override fun setupViews() {
        // Setup Toolbar otomatis mengaktifkan Tombol Kembali
        setupToolbar(binding.toolbar, title = "Lini Produksi A", showBackButton = true)
    }
}