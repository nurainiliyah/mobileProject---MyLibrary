package com.example.mylibrary

data class Magazine(
    val magazineId: String = "",
    val title: String = "",
    val genre: String = "",
    val isAvailable: Boolean = true, // Default to "Available"
    val borrowedBy: String = "", // Nullable, default to null
    val coverImageUrl: String = "",
    val magazineDescription: String = ""
)
