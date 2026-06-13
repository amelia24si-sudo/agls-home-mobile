package com.example.homeapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeapp.databinding.ActivityBaseBinding
import com.google.android.material.tabs.TabLayoutMediator

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "My Library"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.rounded_arrow_back_ios_24)
        }
        binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.black))

        // 1. Inisialisasi ViewPager2 dengan Adapter
        // Pastikan class BaseTabs kamu sudah meng-handle 4 fragment (Home, About, Profile, More)
        val tabsAdapter = BaseTabs(this)
        binding.viewPager.adapter = tabsAdapter

        // 2. Hubungkan TabLayout & ViewPager2 menggunakan TabLayoutMediator
        // Di sini kita mengambil styling (Icon & Text) seperti Bottom Nav sebelumnya
        TabLayoutMediator(binding.bottomTabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.home) // Ganti dengan icon aslimu
                }
                1 -> {
                    tab.text = "About"
                    tab.setIcon(R.drawable.info)
                }
                2 -> {
                    tab.text = "Profile"
                    tab.setIcon(R.drawable.user)
                }
                3 -> {
                    tab.text = "More"
                    tab.setIcon(R.drawable.more)
                }
                4 -> {
                    tab.text = "Note"
                    tab.setIcon(R.drawable.note)
                }
                5 -> {
                    tab.text = "Chat"
                    tab.setIcon(R.drawable.chat)
                }
            }
        }.attach()

        // 3. Handle Back Button Logic
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Jika sedang tidak di tab pertama (Home), balik ke index 0
                if (binding.viewPager.currentItem != 0) {
                    binding.viewPager.currentItem = 0
                } else {
                    // Jika sudah di Home, baru keluar aplikasi
                    finish()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}