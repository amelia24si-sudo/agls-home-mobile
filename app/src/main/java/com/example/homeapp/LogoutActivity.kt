package com.example.homeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeapp.databinding.ActivityLogoutBinding

class LogoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kode ini harus selalu dipanggil saat butuh akses "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        enableEdgeToEdge()
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // AKSI JIKA TEKAN YES (LOGOUT)
        binding.btnyes.setOnClickListener {
            //Hapus sesi login di SharedPreferences
            val logout = sharedPref.edit()
            logout.clear() // Ini akan menghapus isLogin dan username
            logout.apply()

            //Pindah ke halaman Login
            val intent = Intent(this, Login::class.java)
            // Tambahkan flags ini agar user tidak bisa tekan tombol 'Back' ke halaman Home setelah logout
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        binding.btnno.setOnClickListener {
            finish()
        }
    }
}