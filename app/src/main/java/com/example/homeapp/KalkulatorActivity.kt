package com.example.homeapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeapp.databinding.ActivityKalkulatorBinding
import com.google.android.material.snackbar.Snackbar

class KalkulatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKalkulatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKalkulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ==========================================
        // 1. Inisialisasi Komponen Bangun Datar (Segitiga)
        // ==========================================
        val etAlasSegitiga = findViewById<EditText>(R.id.etAlasSegitiga)
        val etTinggiSegitiga = findViewById<EditText>(R.id.etTinggiSegitiga)
        val btnHitungSegitiga = findViewById<Button>(R.id.btnHitungSegitiga)
        val tvHasilSegitiga = findViewById<TextView>(R.id.tvHasilSegitiga)

        // ==========================================
        // 2. Inisialisasi Komponen Bangun Ruang (Balok)
        // ==========================================
        val etPanjangBalok = findViewById<EditText>(R.id.etPanjangBalok)
        val etLebarBalok = findViewById<EditText>(R.id.etLebarBalok)
        val etTinggiBalok = findViewById<EditText>(R.id.etTinggiBalok)
        val btnHitungBalok = findViewById<Button>(R.id.btnHitungBalok)
        val tvHasilBalok = findViewById<TextView>(R.id.tvHasilBalok)

        // ==========================================
        // 3. Logika Perhitungan Luas Segitiga
        // ==========================================
        btnHitungSegitiga.setOnClickListener {
            val alasStr = etAlasSegitiga.text.toString()
            val tinggiStr = etTinggiSegitiga.text.toString()

            if (alasStr.isNotEmpty() && tinggiStr.isNotEmpty()) {
                val alas = alasStr.toDouble()
                val tinggi = tinggiStr.toDouble()

                // Menghitung Luas
                val luas = 0.5 * alas * tinggi
                tvHasilSegitiga.text = "Hasil Luas: $luas"
                Snackbar.make(binding.root, "Luas segitiga telah di hitung", Snackbar.LENGTH_SHORT)
                    .setAction("Tutup"){
                        Log.e("Info Snackbar","Snackbar ditutup")
                    }
                    .show()
                // Menampilkan informasi ke Logcat
                Log.d(TAG, "Menghitung Luas Segitiga -> Alas: $alas, Tinggi: $tinggi, Hasil: $luas")
            } else {
                Toast.makeText(this, "Harap isi alas dan tinggi!", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Gagal menghitung segitiga: Input masih kosong")
            }
        }

        // ==========================================
        // 4. Logika Perhitungan Volume Balok
        // ==========================================
            binding.btnHitungBalok.setOnClickListener {
            val panjangStr = etPanjangBalok.text.toString()
            val lebarStr = etLebarBalok.text.toString()
            val tinggiStr = etTinggiBalok.text.toString()

            if (panjangStr.isNotEmpty() && lebarStr.isNotEmpty() && tinggiStr.isNotEmpty()) {
                val panjang = panjangStr.toDouble()
                val lebar = lebarStr.toDouble()
                val tinggi = tinggiStr.toDouble()

                // Menghitung Volume
                val volume = panjang * lebar * tinggi
                tvHasilBalok.text = "Hasil Volume: $volume"
                Snackbar.make(binding.root, "Volume balok telah di hitung", Snackbar.LENGTH_SHORT)
                    .setAction("Tutup"){
                        Log.e("Info Snackbar","Snackbar ditutup")
                    }
                    .show()
                // Menampilkan informasi ke Logcat
                Log.d(TAG, "Menghitung Volume Balok -> P: $panjang, L: $lebar, T: $tinggi, Hasil: $volume")
            } else {
                Toast.makeText(this, "Harap isi panjang, lebar, dan tinggi!", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Gagal menghitung balok: Input masih kosong")
            }
        }

        binding.kembali.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            finish()
        }
    }
}