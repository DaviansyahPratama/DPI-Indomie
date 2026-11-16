package com.example.RengginangSabit_DPI.orders

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentOrdersBinding

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private lateinit var orderAdapter: OrderAdapter
    private val allOrders = listOf(
        Order("#ORD-2026-001", "Toko Berkah Jaya", "15 Okt 2026", 50, "Rp 2.500.000", "Diproses"),
        Order("#ORD-2026-002", "Agen Sembako Gurih", "16 Okt 2026", 120, "Rp 6.000.000", "Baru"),
        Order("#ORD-2026-003", "Distributor Rengginang Utama", "14 Okt 2026", 200, "Rp 10.000.000", "Selesai"),
        Order("#ORD-2026-004", "Warung Bu Siti", "17 Okt 2026", 30, "Rp 1.500.000", "Baru"),
        Order("#ORD-2026-005", "Minimarket Madura Sabit", "13 Okt 2026", 80, "Rp 4.000.000", "Selesai"),
        Order("#ORD-2026-006", "Koperasi Karyawan DPI", "12 Okt 2026", 150, "Rp 7.500.000", "Diproses")
    )

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Daftar Pesanan", showBackButton = false)

        // 2. Setup RecyclerView & CustomAdapter
        orderAdapter = OrderAdapter(allOrders) { selectedOrder ->
            Toast.makeText(
                requireContext(),
                "Pesanan ${selectedOrder.id} (${selectedOrder.customerName}) dipilih",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }

        // 3. Input Pencarian Live Search
        binding.etSearchOrder.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().trim()
            val filtered = allOrders.filter { order ->
                order.customerName.contains(query, ignoreCase = true) ||
                        order.id.contains(query, ignoreCase = true)
            }
            orderAdapter.updateData(filtered)
        }

        // 4. FAB Tambah Pesanan
        binding.fabAddOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Tambah Pesanan Baru", Toast.LENGTH_SHORT).show()
        }
    }
}
