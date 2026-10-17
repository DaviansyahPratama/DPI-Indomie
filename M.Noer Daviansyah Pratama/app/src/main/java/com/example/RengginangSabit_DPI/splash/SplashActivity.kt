package com.example.RengginangSabit_DPI.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun setupViews() {
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            // Cek status isLogin dari SharedPreferences
            val sharedPref = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            val intent = if (isLogin) {
                // Jika sudah login, langsung ke MainActivity
                Intent(this, MainActivity::class.java)
            } else {
                // Jika belum login, ke LoginActivity
                Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 2000)
    }
}
