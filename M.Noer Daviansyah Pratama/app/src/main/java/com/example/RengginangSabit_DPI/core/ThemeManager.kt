package com.example.RengginangSabit_DPI.core

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * Manager untuk mengelola preferensi dan penerapan Dark Mode (Mode Gelap)
 */
object ThemeManager {
    private const val PREF_NAME = "THEME_SETTINGS"
    private const val KEY_DARK_MODE = "DARK_MODE"

    /**
     * Terapkan tema (Light/Dark) berdasarkan preferensi yang tersimpan
     */
    fun applyTheme(context: Context) {
        val isDark = isDarkMode(context)
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    /**
     * Simpan status Dark Mode baru dan perbarui tema aplikasi secara dinamis
     */
    fun setDarkMode(context: Context, enabled: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        applyTheme(context)
    }

    /**
     * Ambil status tersimpan dari SharedPreferences (Default: false / Light Mode)
     */
    fun isDarkMode(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_DARK_MODE, false)
    }
}
