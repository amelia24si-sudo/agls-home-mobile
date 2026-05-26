import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeapp.More.MenuAdapter
import com.example.homeapp.More.MenuFeature
import com.example.homeapp.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    // Data yang ingin ditampilkan
    private val listFitur = listOf(
        MenuFeature(
            "Produk",
            "Tambah Produk",
            "Fitur untuk mengunggah komoditas atau barang dagangan baru ke sistem, lengkap dengan pengaturan harga, stok, dan kategori produk.",
            "https://images.unsplash.com/photo-1523275335684-37898b6baf30?q=80&w=400&h=300&auto=format&fit=crop"
        ),
        MenuFeature(
            "UMKM",
            "Tambah UMKM",
            "Modul pendaftaran unit usaha baru bagi warga guna memetakan potensi ekonomi lokal dan integrasi ke ekosistem digital.",
            "https://images.unsplash.com/photo-1542838132-92c53300491e?q=80&w=400&h=300&auto=format&fit=crop"
        ),
        MenuFeature(
            "Pesanan",
            "Tambah Pesanan",
            "Sistem pencatatan transaksi masuk untuk mendokumentasikan setiap penjualan yang terjadi baik secara online maupun offline.",
            "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?q=80&w=400&h=300&auto=format&fit=crop"
        ),
        MenuFeature(
            "Pengguna",
            "Kelola User",
            "Panel kontrol untuk mengatur akun administrator dan staf aplikasi dalam menjaga keamanan serta distribusi wewenang akses.",
            "https://images.unsplash.com/photo-1454165833767-027ffea9e77b?q=80&w=400&h=300&auto=format&fit=crop"
        ),
        MenuFeature(
            "Pengguna",
            "Kelola Warga",
            "Pusat data informasi penduduk yang memungkinkan pemutakhiran profil warga secara real-time untuk keperluan pelayanan publik.",
            "https://images.unsplash.com/photo-1517048676732-d65bc937f952?q=80&w=400&h=300&auto=format&fit=crop"
        ),
        MenuFeature(
            "UMKM",
            "Tampil UMKM",
            "Berpusat untuk menampilkan semua UMKM yang terdaftar dengan fitur rating produk UMKM yang telah diverifikasi oleh sistem.",
            "https://images.unsplash.com/photo-1441986300917-64674bd600d8?q=80&w=400&h=300&auto=format&fit=crop"
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.rvMenuFeatures.layoutManager = LinearLayoutManager(requireContext())

        // Menginisialisasi adapter dengan lambda untuk handle klik
        val adapter = MenuAdapter(listFitur) { feature ->
            Toast.makeText(requireContext(), "Membuka: ${feature.subTitle}", Toast.LENGTH_SHORT)
                .show()
        }

        binding.rvMenuFeatures.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}