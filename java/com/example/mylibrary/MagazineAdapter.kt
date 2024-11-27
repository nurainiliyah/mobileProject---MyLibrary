package com.example.mylibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MagazineAdapter(
    private val magazineList: List<Magazine>,
    private val clickListener: (Magazine) -> Unit
) : RecyclerView.Adapter<MagazineAdapter.MagazineViewHolder>() {

    class MagazineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvMagazineTitle)
        val genre: TextView = itemView.findViewById(R.id.tvMagazineGenre)
        val coverImage: ImageView = itemView.findViewById(R.id.ivMagazineCover)
        val status: TextView = itemView.findViewById(R.id.tvMagazineStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MagazineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.magazine_item, parent, false)
        return MagazineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MagazineViewHolder, position: Int) {
        val magazine = magazineList[position]
        holder.title.text = magazine.title
        holder.genre.text = "Genre: ${magazine.genre}"
        holder.status.text = if (magazine.isAvailable) "Available" else "Borrowed"

        // Load cover image with Glide
        Glide.with(holder.itemView.context)
            .load(magazine.coverImageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.coverImage)

        // Handle click events
        holder.itemView.setOnClickListener {
            clickListener(magazine)
        }
    }

    override fun getItemCount(): Int = magazineList.size
}
