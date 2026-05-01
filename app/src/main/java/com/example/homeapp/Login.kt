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

class Login : AppCompatActivity() {
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
            val user = binding.Username.text.toString()
            val pass = binding.Password.text.toString()
            if (user.isNotEmpty() && pass.isNotEmpty()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin melanjutkan?")
                    .setPositiveButton("Ya") { dialog, _ ->
                        val editor = sharedPref.edit()
                        editor.putBoolean("isLogin", true)
                        editor.putString("username", user)
                        editor.apply()
                        // Gunakan this@Login untuk memastikan context merujuk pada Activity
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "Anda telah login, selamat datang $user!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        Log.e("Info Dialog", "Anda memilih Ya!")
                    }
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                        Log.e("Info Dialog", "Anda memilih Tidak!")
                    }
                    .show()
            } else {
                Toast.makeText(this, "Harap isi username dan password anda!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}