package com.example.homeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.homeapp.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Kode ini harus selalu dipanggil saat butuh akses "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnlogin.setOnClickListener {
            val inputUser = binding.Username.text.toString()
            val inputPass = binding.Password.text.toString()

            // Ambil data yang didaftarkan saat Registrasi
            val savedUser = sharedPref.getString("reg_username", "")
            val savedPass = sharedPref.getString("reg_password", "")

            // Cek Rule: (Username=Pass) ATAU (Input sesuai Data Registrasi)
            if ((inputUser == inputPass) || (inputUser == savedUser && inputPass == savedPass)) {
                // SIMPAN SESSION LOGIN
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true) // Ini yang membuat user tidak perlu login lagi nanti
                editor.putString("username", inputUser)
                editor.apply()

                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login Gagal!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}