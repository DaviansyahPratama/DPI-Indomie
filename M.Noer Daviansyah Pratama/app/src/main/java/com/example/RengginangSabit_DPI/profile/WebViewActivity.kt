package com.example.RengginangSabit_DPI.profile

import android.annotation.SuppressLint
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.RengginangSabit_DPI.core.BaseActivity
import com.example.RengginangSabit_DPI.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupViews() {
        // 1. Setup Toolbar dengan tombol kembali ke Profile
        setupToolbar(binding.toolbar, "Website Rengginang Sabit", showBackButton = true)

        val url = intent.getStringExtra("EXTRA_URL") ?: "https://rengginangsabit.vercel.app"

        // 2. Setup WebView
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }

        // 3. Tangani tombol back agar bisa kembali ke halaman web sebelumnya jika ada,
        // jika sudah tidak ada riwayat web, maka kembali ke ProfileActivity
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}
