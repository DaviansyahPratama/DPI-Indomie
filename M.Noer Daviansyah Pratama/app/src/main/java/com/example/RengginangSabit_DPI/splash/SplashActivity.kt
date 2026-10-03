package com.example.RengginangSabit_DPI.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.RengginangSabit_DPI.Auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun setupViews() {
        // Menyembunyikan ActionBar/Toolbar khusus untuk halaman Splash
        supportActionBar?.hide()

        // Delay selama 2 detik lalu otomatis berpindah ke LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Agar saat tombol back ditekan dari Login, tidak balik ke Splash
        }, 2000)
    }
}