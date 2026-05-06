package com.example.homeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeapp.databinding.ActivityLogin2Binding
import com.example.homeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnlogin.setOnClickListener {
            val inputUsername = binding.Username.text.toString()
            val inputPassword = binding.Password.text.toString()

            // Membuka SharedPreferences yang sama ("user_pref")
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

            // Mengambil data yang tersimpan (default value kosong "" jika tidak ada)
            val savedUsername = sharedPref.getString("reg_username", "")
            val savedPassword = sharedPref.getString("reg_password", "")

            if (inputUsername == savedUsername && inputPassword == savedPassword) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                // Pindah ke Dashboard/Home
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username atau Password Salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}