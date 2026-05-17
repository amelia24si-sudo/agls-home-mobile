package com.example.homeapp.More

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import com.example.homeapp.LogoutActivity
import com.example.homeapp.R
import com.example.homeapp.databinding.FragmentMoreBinding
import com.example.homeapp.databinding.FragmentProfileBinding

private var _binding: FragmentMoreBinding? = null
private val binding get() = _binding!!
class MoreFragment : Fragment() {
    private val dataListSkillWithDesc = listOf(
        mapOf("title" to "Kotlin", "desc" to "Aplikasi ini menggunakan Kotlin"),
        mapOf("title" to "React JS", "desc" to "Sedang mempelajari React JS"),
        mapOf("title" to "Python", "desc" to "Sedang mempelajari Python")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Ubah adapter menjadi seperti berikut
        val adapter = SimpleAdapter(
            requireContext(),
            dataListSkillWithDesc,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        // Hubungkan listViewItems dengan adapter (masih sama dengan sebelumnya)
        binding.listViewItems.adapter = adapter
        // Tambahkan aksi saat item di-list diklik
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataListSkillWithDesc[position]
            val title = selectedItem["title"]
            val desc = selectedItem["desc"]
            Toast.makeText(requireContext(), "Kamu memilih: $title ($desc)", Toast.LENGTH_SHORT).show()
        }
    }
}