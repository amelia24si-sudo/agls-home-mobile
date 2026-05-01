package com.example.homeapp.Profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeapp.Home.KalkulatorActivity
import com.example.homeapp.Home.WebViewActivity
import com.example.homeapp.LogoutActivity
import com.example.homeapp.R
import com.example.homeapp.databinding.FragmentHomeBinding
import com.example.homeapp.databinding.FragmentProfileBinding

private var _binding: FragmentProfileBinding? = null
private val binding get() = _binding!!
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LogoutActivity::class.java)
            startActivity(intent)
        }

    }
}