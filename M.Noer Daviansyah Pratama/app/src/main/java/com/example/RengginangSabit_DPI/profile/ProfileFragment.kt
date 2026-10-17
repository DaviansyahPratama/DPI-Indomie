package com.example.RengginangSabit_DPI.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun setupViews() {
        // 1. Setup Toolbar
        setupToolbar(binding.toolbar, "Profil Saya", showBackButton = false)

        // 2. Event Klik Buka Website Vercel Rengginang Sabit
        binding.tvWebsite.setOnClickListener {
            openWebPage("https://rengginangsabit.vercel.app")
        }

        // 3. Event Klik Keluar Aplikasi
        binding.tvLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show()
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

    // Fungsi Helper untuk membuka Website di WebViewActivity
    private fun openWebPage(url: String) {
        val intent = Intent(requireContext(), WebViewActivity::class.java).apply {
            putExtra("EXTRA_URL", url)
        }
        startActivity(intent)
    }
}
