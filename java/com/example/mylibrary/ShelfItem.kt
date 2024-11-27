package com.example.mylibrary

sealed class ShelfItem {
    data class BookItem(val book: BorrowedBook) : ShelfItem()
    data class MagazineItem(val magazine: BorrowedMagazine) : ShelfItem()
}

data class BorrowedBook(
    val bookId: String = "",
    val title: String = "",
    val author: String = ""
)

data class BorrowedMagazine(
    val magazineId: String = "",
    val title: String = "",
    val genre: String = ""
)
