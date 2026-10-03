package com.example.uranus_dapi.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    abstract fun setupViews()

    // Utility opsional untuk mempermudah setup Toolbar di Activity mana saja
    protected fun setupToolbar(toolbar: MaterialToolbar, title: String, showBackButton: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        if (showBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}