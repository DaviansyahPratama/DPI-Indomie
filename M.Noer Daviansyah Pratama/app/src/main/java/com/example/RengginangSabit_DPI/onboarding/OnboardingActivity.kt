package com.example.RengginangSabit_DPI.onboarding

import android.content.Context
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    private val onboardingItems = listOf(
        OnboardingItem(
            "Monitoring Real-Time",
            "Pantau suhu oven, kelembaban, dan laju produksi lini pabrik Rengginang Sabit secara real-time.",
            R.drawable.ic_monitoring
        ),
        OnboardingItem(
            "Manajemen Pesanan",
            "Kelola pesanan pelanggan, status pengolahan, dan pencetakan batch produksi dengan cepat dan mudah.",
            R.drawable.ic_orders
        ),
        OnboardingItem(
            "Laporan & Analisis",
            "Dapatkan grafik statistik produksi harian, mingguan, dan bulanan secara akurat.",
            R.drawable.ic_reports
        )
    )

    override fun setupViews() {
        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPagerOnboarding.adapter = adapter

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPageIndicator.text = "${position + 1} / ${onboardingItems.size}"
                if (position == onboardingItems.size - 1) {
                    binding.btnNext.text = "Mulai"
                } else {
                    binding.btnNext.text = "Lanjut"
                }
            }
        })

        binding.btnSkip.setOnClickListener {
            finishOnboarding()
        }

        binding.btnNext.setOnClickListener {
            val current = binding.viewPagerOnboarding.currentItem
            if (current < onboardingItems.size - 1) {
                binding.viewPagerOnboarding.currentItem = current + 1
            } else {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        // Simpan status bahwa Onboarding sudah dilihat
        val pref = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        pref.edit().putBoolean("hasSeenOnboarding", true).apply()

        // Cek status isLogin
        val sessionPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val isLogin = sessionPref.getBoolean("isLogin", false)

        val intent = if (isLogin) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}
