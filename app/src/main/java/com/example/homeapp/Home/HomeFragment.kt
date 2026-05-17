package com.example.homeapp.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homeapp.R
import com.example.homeapp.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Logika klik tombol Kalkulator
        binding.btnKal.setOnClickListener {
            val intent = Intent(requireContext(), KalkulatorActivity::class.java)
            startActivity(intent)
        }

        // 2. Logika klik tombol UMKM / Bina Desa
        binding.btnumkm.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }

        // 3. Logika Filter Menu menggunakan ChipGroup
        binding.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull() // Ambil ID chip yang dipilih
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                // Menampilkan Toast pemberitahuan filter
                Toast.makeText(requireContext(), "Menampilkan: ${chip.text}", Toast.LENGTH_SHORT).show()
                // Logika menampilkan dan menyembunyikan card
                when (chip.text.toString()) {
                    "Semua" -> {
                        // Tampilkan keduanya
                        binding.materialCardView4.visibility = View.VISIBLE // Card Kalkulator
                        binding.materialCardView3.visibility = View.VISIBLE // Card Bina Desa
                    }
                    "Alat Hitung" -> {
                        // Tampilkan hanya Kalkulator
                        binding.materialCardView4.visibility = View.VISIBLE
                        binding.materialCardView3.visibility = View.GONE
                    }
                    "Project" -> {
                        // Tampilkan hanya Bina Desa
                        binding.materialCardView4.visibility = View.GONE
                        binding.materialCardView3.visibility = View.VISIBLE
                    }
                }
            } else {
                // (Opsional) Jika tidak ada chip yang dipilih, kembalikan ke tampilan "Semua"
                binding.materialCardView4.visibility = View.VISIBLE
                binding.materialCardView3.visibility = View.VISIBLE
            }
        }
    }
    // Mencegah memory leak pada ViewBinding Fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}