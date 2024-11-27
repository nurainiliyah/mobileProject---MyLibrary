package com.example.mylibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.util.Log




class BookAdapter(
    private val bookList: List<Books>,
    private val clickListener: (Books) -> Unit // Lambda for handling clicks
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivCover)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val author: TextView = itemView.findViewById(R.id.tvAuthor)
        val genre: TextView = itemView.findViewById(R.id.tvGenre)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]

        // Load book cover using Glide
        Glide.with(holder.itemView.context)
            .load(book.imageUrl) // URL from the Book object
            .placeholder(R.drawable.placeholder) // Optional: Placeholder while loading
            .error(R.drawable.error) // Optional: Error image if loading fails
            .into(holder.cover)

        holder.title.text = book.title
        holder.author.text = "Author: ${book.author}"
        holder.genre.text = "Genre: ${book.genre}"
        holder.status.text = if (book.isAvailable) "Available" else "Borrowed"
        holder.status.setTextColor(
            if (book.isAvailable)
                holder.itemView.context.getColor(android.R.color.holo_green_dark)
            else
                holder.itemView.context.getColor(android.R.color.holo_red_dark)
        )

        Log.d("BookAdapter", "Loading image URL: ${book.imageUrl}")

        // Handle click events
        holder.itemView.setOnClickListener {
            clickListener(book) // Pass the clicked book to the listener
        }
    }

    override fun getItemCount(): Int = bookList.size
}
