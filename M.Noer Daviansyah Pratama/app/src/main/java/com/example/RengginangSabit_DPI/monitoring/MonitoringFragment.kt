package com.example.RengginangSabit_DPI.monitoring

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.core.DatabaseHelper
import com.example.RengginangSabit_DPI.databinding.FragmentMonitoringBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MonitoringFragment : BaseFragment<FragmentMonitoringBinding>(FragmentMonitoringBinding::inflate) {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var lineAdapter: ProductionLineAdapter

    override fun setupViews() {
        dbHelper = DatabaseHelper(requireContext())

        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Monitoring Produksi", showBackButton = false)

        // 2. Setup RecyclerView & ProductionLineAdapter
        lineAdapter = ProductionLineAdapter(emptyList()) { selectedLine ->
            showLineOptionsDialog(selectedLine)
        }

        binding.rvProductionLines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lineAdapter
        }

        // 3. Load Data Lini Produksi dari Database SQLite
        loadLinesFromDb()

        // 4. CREATE: Tombol + Tambah Lini
        binding.btnAddLine.setOnClickListener {
            showAddLineDialog()
        }
    }

    private fun loadLinesFromDb() {
        val lines = dbHelper.getAllProductionLines()
        lineAdapter.updateData(lines)
    }

    // CREATE: Dialog Tambah Lini Produksi
    private fun showAddLineDialog() {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etName = EditText(context).apply { hint = "Nama Lini (misal: Lini Produksi D)" }
        val etDesc = EditText(context).apply { hint = "Deskripsi Status (misal: Penggorengan Suhu Tinggi)" }
        val etOutput = EditText(context).apply { hint = "Laju Output (misal: 50 kg/jam)" }
        val etTemp = EditText(context).apply { hint = "Suhu Oven (misal: 78°C)" }
        val etHum = EditText(context).apply { hint = "Kelembaban (misal: 41%)" }

        layout.addView(etName)
        layout.addView(etDesc)
        layout.addView(etOutput)
        layout.addView(etTemp)
        layout.addView(etHum)

        MaterialAlertDialogBuilder(context)
            .setTitle("Tambah Lini Produksi Baru")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val name = etName.text.toString().trim()
                val desc = etDesc.text.toString().trim().ifEmpty { "Pemasakan Normal" }
                val output = etOutput.text.toString().trim().ifEmpty { "40 kg/jam" }
                val temp = etTemp.text.toString().trim().ifEmpty { "72°C" }
                val hum = etHum.text.toString().trim().ifEmpty { "42%" }

                if (name.isEmpty()) {
                    Toast.makeText(context, "Nama Lini Produksi wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                dbHelper.insertProductionLine(name, desc, output, temp, hum)
                Toast.makeText(context, "Lini Produksi Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show()
                loadLinesFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Dialog Pilihan Action (Update / Delete)
    private fun showLineOptionsDialog(line: ProductionLine) {
        val options = arrayOf("Edit Lini Produksi", "Hapus Lini Produksi")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Opsi: ${line.lineName}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditLineDialog(line)
                    1 -> confirmDeleteLine(line)
                }
            }
            .show()
    }

    // UPDATE: Dialog Edit Lini Produksi
    private fun showEditLineDialog(line: ProductionLine) {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etName = EditText(context).apply { setText(line.lineName); hint = "Nama Lini" }
        val etDesc = EditText(context).apply { setText(line.statusDesc); hint = "Deskripsi Status" }
        val etOutput = EditText(context).apply { setText(line.outputRate); hint = "Laju Output" }
        val etTemp = EditText(context).apply { setText(line.temperature); hint = "Suhu Oven" }
        val etHum = EditText(context).apply { setText(line.humidity); hint = "Kelembaban" }

        layout.addView(etName)
        layout.addView(etDesc)
        layout.addView(etOutput)
        layout.addView(etTemp)
        layout.addView(etHum)

        MaterialAlertDialogBuilder(context)
            .setTitle("Edit ${line.lineName}")
            .setView(layout)
            .setPositiveButton("Simpan Perubahan") { _, _ ->
                val name = etName.text.toString().trim()
                val desc = etDesc.text.toString().trim()
                val output = etOutput.text.toString().trim()
                val temp = etTemp.text.toString().trim()
                val hum = etHum.text.toString().trim()

                dbHelper.updateProductionLine(line.dbId, name, desc, output, temp, hum)
                Toast.makeText(context, "Lini Produksi Berhasil Diperbarui!", Toast.LENGTH_SHORT).show()
                loadLinesFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // DELETE: Konfirmasi Hapus Lini
    private fun confirmDeleteLine(line: ProductionLine) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Lini Produksi")
            .setMessage("Apakah Anda yakin ingin menghapus ${line.lineName}?")
            .setPositiveButton("Hapus") { _, _ ->
                dbHelper.deleteProductionLine(line.dbId)
                Toast.makeText(requireContext(), "Lini Produksi Berhasil Dihapus!", Toast.LENGTH_SHORT).show()
                loadLinesFromDb()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
