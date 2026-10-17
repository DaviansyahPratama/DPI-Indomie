package com.example.RengginangSabit_DPI.orders

import android.widget.Toast
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentOrdersBinding

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar tanpa back button (karena ini tab utama)
        setupToolbar(binding.toolbar, "Daftar Pesanan", showBackButton = false)

        // 2. Event Klik Floating Action Button (+ Tambah Pesanan)
        binding.fabAddOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Tambah Pesanan Baru", Toast.LENGTH_SHORT).show()
        }
    }
}
