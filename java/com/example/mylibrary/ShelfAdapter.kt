package com.example.mylibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShelfAdapter(
    private val items: List<ShelfItem>,
    private val onReturnClick: (ShelfItem) -> Unit
) : RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder>() {

    class ShelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvShelfTitle)
        val authorOrGenre: TextView = itemView.findViewById(R.id.tvShelfAuthor)
        val btnReturn: Button = itemView.findViewById(R.id.btnShelfReturn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelf_item, parent, false)
        return ShelfViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShelfViewHolder, position: Int) {
        val item = items[position]

        when (item) {
            is ShelfItem.BookItem -> {
                holder.title.text = item.book.title
                holder.authorOrGenre.text = "Author: ${item.book.author}"
                holder.btnReturn.setOnClickListener { onReturnClick(item) }
            }
            is ShelfItem.MagazineItem -> {
                holder.title.text = item.magazine.title
                holder.authorOrGenre.text = "Genre: ${item.magazine.genre}"
                holder.btnReturn.setOnClickListener { onReturnClick(item) }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}



