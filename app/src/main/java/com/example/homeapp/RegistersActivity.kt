package com.example.homeapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeapp.databinding.ActivityLoginBinding
import com.example.homeapp.databinding.ActivityRegistersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class RegistersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences untuk menyimpan data registrasi
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this,   LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Setup Spinner untuk Agama
        val agamaList =
            arrayOf("Islam", "Kristen Protestan", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, agamaList)
        binding.spinnerAgama.adapter = adapter

        // 2. Setup DatePickerDialog untuk Tanggal Lahir
        binding.DOB.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.DOB.setText(date)
                    // Hapus error jika user sudah memilih tanggal
                    binding.DOB.error = null
                }, year, month, day)
            datePickerDialog.show()
        }

        // 3. Membersihkan error pada RadioButton saat user memilih salah satu
        binding.rgGender.setOnCheckedChangeListener { _, _ ->
            binding.rbPerempuan.error = null // Menghapus tanda error jika gender sudah dipilih
        }
        // 4. Aksi Tombol Register
        binding.btnregister.setOnClickListener {
            // Ambil semua nilai dari inputan
            val nama = binding.Nama.text.toString().trim()
            val dob = binding.DOB.text.toString().trim()
            val username = binding.Username.text.toString().trim()
            val password = binding.Password.text.toString().trim()
            val confirmPassword = binding.ConfirmPassword.text.toString().trim()
            val agama = binding.spinnerAgama.selectedItem?.toString() ?: ""
            val selectedGenderId = binding.rgGender.checkedRadioButtonId

            var isValid = true
            if (nama.isEmpty()) {
                binding.Nama.error = "Nama Lengkap wajib diisi!"
                isValid = false
            }

            if (dob.isEmpty()) {
                binding.DOB.error = "Tanggal Lahir wajib diisi!"
                isValid = false
            }

            var gender = ""
            if (selectedGenderId == -1) {
                // Menampilkan error pada RadioButton terakhir agar user sadar ada yang kurang
                binding.rbPerempuan.error = "Pilih jenis kelamin!"
                isValid = false
            } else {
                gender = findViewById<RadioButton>(selectedGenderId).text.toString()
                binding.rbPerempuan.error = null
            }

            if (username.isEmpty()) {
                binding.Username.error = "Username wajib diisi!"
                isValid = false
            }

            // --- VALIDASI 3: Password & Confirm Password ---

            if (password.isEmpty()) {
                binding.Password.error = "Password wajib diisi!"
                isValid = false
            }

            if (confirmPassword.isEmpty()) {
                binding.ConfirmPassword.error = "Konfirmasi Password wajib diisi!"
                isValid = false
            } else if (password != confirmPassword) {
                // Pesan khusus jika password tidak cocok
                binding.ConfirmPassword.error = "Password tidak cocok!"
                isValid = false
            }

            // --- VALIDASI TAMBAHAN: Validasi Agama (Jika perlu) ---
            if (agama.isEmpty()) {
                Toast.makeText(this, "Pilih agama Anda!", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // --- PROSES SIMPAN (VALIDASI LULUS) ---
            if (isValid) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Simpan data registrasi?")
                    .setPositiveButton("Simpan") { _, _ ->

                        // --- VALIDASI 4: Simpan ke SharedPreferences ---
                        val editor = sharedPref.edit()
                        editor.putString("reg_nama", nama)
                        editor.putString("reg_dob", dob)
                        editor.putString("reg_gender", gender)
                        editor.putString("reg_agama", agama)
                        editor.putString("reg_username", username)
                        editor.putString("reg_password", password) // Disimpan untuk cek login
                        editor.apply()

                        Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            } else {
                Toast.makeText(this, "Mohon lengkapi/perbaiki data yang salah", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}