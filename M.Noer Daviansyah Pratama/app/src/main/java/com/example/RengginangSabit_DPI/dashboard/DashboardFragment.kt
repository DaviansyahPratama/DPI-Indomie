package com.example.RengginangSabit_DPI.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.core.ThemeManager
import com.example.RengginangSabit_DPI.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    override fun setupViews() {
        // 1. Profile Button Click -> Navigasi ke Tab Profile pada MainActivity
        binding.btnProfile.setOnClickListener {
            (activity as? MainActivity)?.selectBottomNavItem(R.id.nav_profile)
        }

        // 2. Setup Listener Tombol Logout (Toolbar & Tombol Bawah)
        binding.btnLogoutToolbar.setOnClickListener {
            logoutUser()
        }

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        // 3. Setup MPAndroidChart Grafik Statistik Produksi
        setupChartPeriodToggle()
        showChartData(PeriodType.HARIAN)
    }

    private enum class PeriodType {
        HARIAN, MINGGUAN, BULANAN
    }

    private fun setupChartPeriodToggle() {
        binding.toggleGroupPeriode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnPeriodeHarian -> showChartData(PeriodType.HARIAN)
                    R.id.btnPeriodeMingguan -> showChartData(PeriodType.MINGGUAN)
                    R.id.btnPeriodeBulanan -> showChartData(PeriodType.BULANAN)
                }
            }
        }
    }

    private fun showChartData(periodType: PeriodType) {
        val (labels, entries, totalText) = when (periodType) {
            PeriodType.HARIAN -> Triple(
                listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"),
                listOf(
                    BarEntry(0f, 180f),
                    BarEntry(1f, 210f),
                    BarEntry(2f, 195f),
                    BarEntry(3f, 240f),
                    BarEntry(4f, 220f),
                    BarEntry(5f, 150f),
                    BarEntry(6f, 50f)
                ),
                "Total Produksi Harian: 1,245 kg"
            )
            PeriodType.MINGGUAN -> Triple(
                listOf("Mkg 1", "Mkg 2", "Mkg 3", "Mkg 4"),
                listOf(
                    BarEntry(0f, 1200f),
                    BarEntry(1f, 1450f),
                    BarEntry(2f, 1380f),
                    BarEntry(3f, 1600f)
                ),
                "Total Produksi Mingguan: 5,630 kg"
            )
            PeriodType.BULANAN -> Triple(
                listOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun"),
                listOf(
                    BarEntry(0f, 4500f),
                    BarEntry(1f, 5200f),
                    BarEntry(2f, 4800f),
                    BarEntry(3f, 6100f),
                    BarEntry(4f, 5900f),
                    BarEntry(5f, 6400f)
                ),
                "Total Produksi Bulanan: 32,900 kg"
            )
        }

        binding.tvTotalProduksiChart.text = totalText

        if (entries.isEmpty()) {
            binding.barChartProduction.visibility = View.GONE
            binding.tvEmptyChart.visibility = View.VISIBLE
            return
        } else {
            binding.barChartProduction.visibility = View.VISIBLE
            binding.tvEmptyChart.visibility = View.GONE
        }

        val isDark = ThemeManager.isDarkMode(requireContext())
        val textColor = if (isDark) Color.parseColor("#EEEEEE") else Color.parseColor("#252525")
        val orangeColor = ContextCompat.getColor(requireContext(), R.color.primary_orange)

        val dataSet = BarDataSet(entries, "Jumlah Produksi (kg)").apply {
            color = orangeColor
            valueTextColor = textColor
            valueTextSize = 10f
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.5f
        }

        binding.barChartProduction.apply {
            data = barData
            description.isEnabled = false
            legend.textColor = textColor
            setFitBars(true)
            animateY(800)

            // Setup X-Axis
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(labels)
                granularity = 1f
                isGranularityEnabled = true
                this.textColor = textColor
                setDrawGridLines(false)
            }

            // Setup Y-Axis Left & Right
            axisLeft.apply {
                this.textColor = textColor
                axisMinimum = 0f
            }
            axisRight.isEnabled = false

            invalidate()
        }
    }

    private fun logoutUser() {
        val sharedPref = requireActivity().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLogin", false)
        editor.apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
