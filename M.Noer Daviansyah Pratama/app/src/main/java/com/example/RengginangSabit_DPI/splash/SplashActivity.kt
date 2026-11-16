package com.example.RengginangSabit_DPI.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivitySplashBinding
import com.example.RengginangSabit_DPI.onboarding.OnboardingActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun setupViews() {
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            // 1. Cek apakah pengguna sudah melihat Onboarding
            val appPref = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
            val hasSeenOnboarding = appPref.getBoolean("hasSeenOnboarding", false)

            if (!hasSeenOnboarding) {
                val intent = Intent(this, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
                return@postDelayed
            }

            // 2. Cek status isLogin dari SharedPreferences
            val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            val intent = if (isLogin) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 2000)
    }
}
