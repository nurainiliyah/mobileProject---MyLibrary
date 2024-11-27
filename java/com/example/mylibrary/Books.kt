package com.example.mylibrary

data class Books(
    val bookId: String = "",
    val title: String = "",
    val author: String = "",
    val genre: String = "",
    val isAvailable: Boolean = true, // Default to "Available"
    val borrowedBy: String? = null, // Nullable, default to null
    val imageUrl: String = ""
)
