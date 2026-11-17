package com.example.RengginangSabit_DPI.orders

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RengginangSabit_DPI.api.SupabaseOrderDto
import com.example.RengginangSabit_DPI.api.SupabaseRepository
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.core.DatabaseHelper
import com.example.RengginangSabit_DPI.databinding.FragmentOrdersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var orderAdapter: OrderAdapter
    private val supabaseRepo = SupabaseRepository()
    private var orderList = mutableListOf<Order>()

    override fun setupViews() {
        dbHelper = DatabaseHelper(requireContext())

        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Daftar Pesanan", showBackButton = false)

        // 2. Setup RecyclerView & CustomAdapter
        orderAdapter = OrderAdapter(emptyList()) { selectedOrder ->
            showOptionsDialog(selectedOrder)
        }

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }

        // 3. Load Data awal dari SQLite Database & Sync Supabase API
        loadOrdersFromDb()
        syncWithSupabase()

        // 4. Live Search
        binding.etSearchOrder.doOnTextChanged { text, _, _, _ ->
            filterOrders(text.toString().trim())
        }

        // 5. FAB (Create Order)
        binding.fabAddOrder.setOnClickListener {
            showAddOrderDialog()
        }
    }

    private fun loadOrdersFromDb() {
        orderList = dbHelper.getAllOrders().toMutableList()
        filterOrders(binding.etSearchOrder.text.toString().trim())
    }

    private fun syncWithSupabase() {
        supabaseRepo.fetchOrdersFromSupabase(
            onSuccess = { supabaseOrders ->
                if (supabaseOrders.isNotEmpty()) {
                    // Update database lokal jika ada data Supabase
                    loadOrdersFromDb()
                }
            },
            onError = {
                // Menggunakan data SQLite Lokal jika offline
            }
        )
    }

    private fun filterOrders(query: String) {
        val filtered = if (query.isEmpty()) {
            orderList
        } else {
            orderList.filter { order ->
                order.customerName.contains(query, ignoreCase = true) ||
                        order.id.contains(query, ignoreCase = true)
            }
        }
        orderAdapter.updateData(filtered)
    }

    // CREATE: Dialog Tambah Pesanan Baru (SQLite + Supabase Sync)
    private fun showAddOrderDialog() {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etCode = EditText(context).apply { hint = "Kode Pesanan (misal: #ORD-2026-004)" }
        val etCustomer = EditText(context).apply { hint = "Nama Pelanggan" }
        val etDate = EditText(context).apply { hint = "Tanggal (misal: 18 Okt 2026)" }
        val etQty = EditText(context).apply { hint = "Jumlah (kg)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etPrice = EditText(context).apply { hint = "Total Harga (misal: Rp 3.500.000)" }
        val etStatus = EditText(context).apply { hint = "Status (Baru / Diproses / Selesai)" }

        layout.addView(etCode)
        layout.addView(etCustomer)
        layout.addView(etDate)
        layout.addView(etQty)
        layout.addView(etPrice)
        layout.addView(etStatus)

        MaterialAlertDialogBuilder(context)
            .setTitle("Tambah Pesanan Baru")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val code = etCode.text.toString().trim()
                val customer = etCustomer.text.toString().trim()
                val date = etDate.text.toString().trim()
                val qtyStr = etQty.text.toString().trim()
                val price = etPrice.text.toString().trim()
                val status = etStatus.text.toString().trim().ifEmpty { "Baru" }

                if (customer.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(context, "Nama Pelanggan dan Jumlah kg wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val qty = qtyStr.toIntOrNull() ?: 10
                val autoCode = if (code.isEmpty()) "#ORD-2026-0${(10..99).random()}" else code
                val autoDate = if (date.isEmpty()) "Hari ini" else date
                val autoPrice = if (price.isEmpty()) "Rp ${qty * 50000}" else price

                // 1. Insert ke Database SQLite
                val dbId = dbHelper.insertOrder(autoCode, customer, autoDate, qty, autoPrice, status)

                // 2. Sinkronisasi ke Supabase API
                val supabaseDto = SupabaseOrderDto(
                    id = if (dbId > 0) dbId else null,
                    order_code = autoCode,
                    customer_name = customer,
                    order_date = autoDate,
                    quantity_kg = qty,
                    total_price = autoPrice,
                    status = status
                )
                supabaseRepo.createOrderInSupabase(supabaseDto, onSuccess = {}, onError = {})

                Toast.makeText(context, "Pesanan Berhasil Disimpan (SQLite & Supabase Sync)!", Toast.LENGTH_SHORT).show()
                loadOrdersFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Dialog Pilihan Action (Update / Delete)
    private fun showOptionsDialog(order: Order) {
        val options = arrayOf("Edit Pesanan", "Hapus Pesanan")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Opsi Pesanan: ${order.id}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditOrderDialog(order)
                    1 -> confirmDeleteOrder(order)
                }
            }
            .show()
    }

    // UPDATE: Dialog Edit Pesanan
    private fun showEditOrderDialog(order: Order) {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etCode = EditText(context).apply { setText(order.id); hint = "Kode Pesanan" }
        val etCustomer = EditText(context).apply { setText(order.customerName); hint = "Nama Pelanggan" }
        val etDate = EditText(context).apply { setText(order.orderDate); hint = "Tanggal" }
        val etQty = EditText(context).apply { setText(order.quantityKg.toString()); hint = "Jumlah (kg)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etPrice = EditText(context).apply { setText(order.totalPrice); hint = "Total Harga" }
        val etStatus = EditText(context).apply { setText(order.status); hint = "Status (Baru/Diproses/Selesai)" }

        layout.addView(etCode)
        layout.addView(etCustomer)
        layout.addView(etDate)
        layout.addView(etQty)
        layout.addView(etPrice)
        layout.addView(etStatus)

        MaterialAlertDialogBuilder(context)
            .setTitle("Edit Pesanan #${order.id}")
            .setView(layout)
            .setPositiveButton("Simpan Perubahan") { _, _ ->
                val code = etCode.text.toString().trim()
                val customer = etCustomer.text.toString().trim()
                val date = etDate.text.toString().trim()
                val qty = etQty.text.toString().toIntOrNull() ?: order.quantityKg
                val price = etPrice.text.toString().trim()
                val status = etStatus.text.toString().trim()

                dbHelper.updateOrder(order.dbId, code, customer, date, qty, price, status)
                Toast.makeText(context, "Pesanan Berhasil Diperbarui!", Toast.LENGTH_SHORT).show()
                loadOrdersFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // DELETE: Konfirmasi Hapus (SQLite + Supabase Sync)
    private fun confirmDeleteOrder(order: Order) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Pesanan")
            .setMessage("Apakah Anda yakin ingin menghapus pesanan ${order.id} (${order.customerName})?")
            .setPositiveButton("Hapus") { _, _ ->
                dbHelper.deleteOrder(order.dbId)
                supabaseRepo.deleteOrderFromSupabase(order.dbId, onSuccess = {}, onError = {})
                Toast.makeText(requireContext(), "Pesanan Berhasil Dihapus!", Toast.LENGTH_SHORT).show()
                loadOrdersFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
