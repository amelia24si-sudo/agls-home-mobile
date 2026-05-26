package com.example.homeapp

import MoreFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homeapp.About.AboutFragment
import com.example.homeapp.Home.HomeFragment
import com.example.homeapp.Profile.ProfileFragment

class BaseTabs (activity: FragmentActivity) : FragmentStateAdapter(activity){
    // Jumlah total tab yang ada
    override fun getItemCount(): Int = 4

    // Menentukan Fragment mana yang akan ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> AboutFragment()
            2 -> ProfileFragment()
            3 -> MoreFragment()
            else -> HomeFragment()
        }
    }
}