package com.example.RengginangSabit_DPI.dashboard

import android.content.Context
import android.content.Intent
import com.example.RengginangSabit_DPI.MainActivity
import com.example.RengginangSabit_DPI.R
import com.example.RengginangSabit_DPI.auth.LoginActivity
import com.example.RengginangSabit_DPI.core.BaseFragment
import com.example.RengginangSabit_DPI.databinding.FragmentDashboardBinding

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
    }

    // Fungsi untuk menghapus sesi dan kembali ke LoginActivity
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
