package com.example.homeapp.Home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeapp.data.model.Article
import com.example.homeapp.databinding.ItemNewsBinding
class NewsAdapter(
    private val articles: List<Article>,
    private val isHorizontal: Boolean = false
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Trik: Jika mode horizontal, batasi lebar item agar tidak memenuhi layar (bisa digeser)
        if (isHorizontal) {
            val params = binding.root.layoutParams
            params.width = (parent.context.resources.displayMetrics.widthPixels * 0.7).toInt() // 70% lebar layar
            binding.root.layoutParams = params
        }
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.tvTitle.text = article.title
        holder.binding.tvDesc.text = article.description ?: "Tidak ada deskripsi tambahan."
        Glide.with(holder.itemView.context)
            .load(article.image_url)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .into(holder.binding.imgBerita)
    }
    override fun getItemCount(): Int = articles.size
}