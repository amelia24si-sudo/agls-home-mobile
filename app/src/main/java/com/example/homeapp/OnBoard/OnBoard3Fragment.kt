package com.example.homeapp.OnBoard
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.homeapp.R
import com.example.homeapp.RegistersActivity
class OnBoard3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment 3
        val view = inflater.inflate(R.layout.fragment_on_board3, container, false)
        // Inisialisasi tombol START
        val btnStart = view.findViewById<Button>(R.id.btnStart)
        // Aksi klik menuju ke halaman Register
        btnStart.setOnClickListener {
            val intent = Intent(requireContext(), RegistersActivity::class.java)
            startActivity(intent)
            // Menutup OnBoardActivity agar tidak bisa ditekan tombol back
            requireActivity().finish()
        }
        return view
    }
}