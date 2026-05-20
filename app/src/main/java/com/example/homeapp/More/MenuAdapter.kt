package com.example.homeapp.More

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeapp.databinding.ItemFeatureBinding

class MenuAdapter(
    private val list: List<MenuFeature>,
    private val onClick: (MenuFeature) -> Unit
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemFeatureBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeatureBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvKategori.text = item.title
            tvJudul.text = item.subTitle
            tvDeskripsi.text = item.desc

            Glide.with(holder.itemView.context)
                .load(item.img) // Memanggil URL String nyata tadi
                .into(ivFeatureIcon)  // Memasukkan ke ImageView di XML

            // Handle Klik pada satu kartu
            root.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemCount(): Int = list.size
}