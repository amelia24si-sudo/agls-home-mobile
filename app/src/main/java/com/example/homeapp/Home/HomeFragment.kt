package com.example.homeapp.Home
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeapp.data.api.ApiClient
import com.example.homeapp.data.model.NewsResponse
import com.example.homeapp.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Simpan status mode tampilan di sini (Ubah ke true jika ingin horizontal secara default)
    private val useHorizontalMode = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // --- KONFIGURASI RECYCLERVIEW (PILIH SALAH SATU) ---
        if (useHorizontalMode) {
            // A. MODE HORIZONTAL (Geser Samping)
            binding.rvBerita.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        } else {
            // B. MODE GRID (2 Kolom ke bawah)
            binding.rvBerita.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        binding.rvBerita.isNestedScrollingEnabled = false
        // Eksekusi Ambil Data API Newsdata.io
        fetchNewsData()
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
            val selectedChipId = checkedIds.firstOrNull()
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                Toast.makeText(requireContext(), "Menampilkan: ${chip.text}", Toast.LENGTH_SHORT).show()
                when (chip.text.toString()) {
                    "Semua" -> {
                        binding.materialCardView4.visibility = View.VISIBLE
                        binding.materialCardView3.visibility = View.VISIBLE
                    }
                    "Alat Hitung" -> {
                        binding.materialCardView4.visibility = View.VISIBLE
                        binding.materialCardView3.visibility = View.GONE
                    }
                    "Project" -> {
                        binding.materialCardView4.visibility = View.GONE
                        binding.materialCardView3.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.materialCardView4.visibility = View.VISIBLE
                binding.materialCardView3.visibility = View.VISIBLE
            }
        }
    }
    private fun fetchNewsData() {
        ApiClient.instance.getEcommerceNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()!!.results
                    // Set adapter dengan membawa konfigurasi mode horizontal/grid
                    val adapter = NewsAdapter(articles, isHorizontal = useHorizontalMode)
                    binding.rvBerita.adapter = adapter
                } else {
                    val errorLog = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Gagal: Kesalahan API / Key Tidak Valid ($errorLog)", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Masalah Jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}