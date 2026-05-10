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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences untuk menyimpan data registrasi
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Setup Spinner untuk Agama
        val agamaList = arrayOf("Islam", "Kristen Protestan", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, agamaList)
        binding.spinnerAgama.adapter = adapter

        // 2. Setup DatePickerDialog untuk Tanggal Lahir
        binding.DOB.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
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
            // Ambil semua nilai
            val nama = binding.Nama.text.toString().trim()
            val dob = binding.DOB.text.toString().trim()
            val username = binding.Username.text.toString().trim()
            val password = binding.Password.text.toString().trim()
            val confirmPassword = binding.ConfirmPassword.text.toString().trim()
            val agama = binding.spinnerAgama.selectedItem?.toString() ?: ""

            // Ambil id RadioButton yang dipilih
            val selectedGenderId = binding.rgGender.checkedRadioButtonId

            var isValid = true

            // --- VALIDASI ERROR PADA MASING-MASING KOLOM ---

            if (nama.isEmpty()) {
                binding.Nama.error = "Nama Lengkap tidak boleh kosong!"
                binding.Nama.requestFocus()
                isValid = false
            }

            if (dob.isEmpty()) {
                binding.DOB.error = "Tanggal Lahir harus diisi!"
                if (isValid) binding.DOB.requestFocus()
                isValid = false
            }

            var gender = ""
            if (selectedGenderId == -1) {
                // Untuk RadioGroup, kita menempelkan error pada salah satu RadioButton (biasanya yang terakhir)
                binding.rbPerempuan.error = "Pilih jenis kelamin Anda!"
                isValid = false
            } else {
                gender = findViewById<RadioButton>(selectedGenderId).text.toString()
            }

            if (username.isEmpty()) {
                binding.Username.error = "Username tidak boleh kosong!"
                if (isValid) binding.Username.requestFocus()
                isValid = false
            }

            if (password.isEmpty()) {
                binding.Password.error = "Password tidak boleh kosong!"
                if (isValid) binding.Password.requestFocus()
                isValid = false
            } else if (password.length < 6) { // Tambahan opsional: batas minimal password
                binding.Password.error = "Password minimal 6 karakter!"
                if (isValid) binding.Password.requestFocus()
                isValid = false
            }

            if (confirmPassword.isEmpty()) {
                binding.ConfirmPassword.error = "Harap konfirmasi password Anda!"
                if (isValid) binding.ConfirmPassword.requestFocus()
                isValid = false
            } else if (password != confirmPassword) {
                binding.ConfirmPassword.error = "Password dan Confirm Password tidak sama!"
                if (isValid) binding.ConfirmPassword.requestFocus()
                isValid = false
            }
            if (isValid && username != password) {
                binding.Password.error = "Username dan Password harus sama untuk mendaftar!"
                binding.Username.error = "Username dan Password harus sama!"
                if (isValid) binding.Username.requestFocus()
                isValid = false
            }

            // --- JIKA SEMUA VALIDASI LULUS ---
            if (isValid) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Konfirmasi Registrasi")
                    .setMessage("Apakah data yang Anda masukkan sudah benar?")
                    .setPositiveButton("Ya, Simpan") { dialog, _ ->

                        // Simpan seluruh isian ke SharedPreferences untuk dicek saat Login nanti
                        val editor = sharedPref.edit()
                        editor.putString("reg_nama", nama)
                        editor.putString("reg_dob", dob)
                        editor.putString("reg_gender", gender)
                        editor.putString("reg_agama", agama)
                        editor.putString("reg_username", username)
                        editor.putString("reg_password", password)
                        editor.apply()

                        Toast.makeText(this, "Registrasi berhasil! Silakan Login.", Toast.LENGTH_SHORT).show()

                        // Pindah ke Halaman Login (atau finish jika activity ini dipanggil dari Login)
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()

                        dialog.dismiss()
                    }
                    .setNegativeButton("Periksa Kembali") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}
