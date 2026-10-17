package com.example.RengginangSabit_DPI.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Utility helper untuk mengganti Fragment di FragmentManager
 */
fun FragmentManager.replaceFragment(containerId: Int, fragment: Fragment) {
    beginTransaction()
        .replace(containerId, fragment)
        .commit()
}
