package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import android.widget.ImageButton

class MainActivity6 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val borrowedItems = mutableListOf<ShelfItem>()
    private lateinit var adapter: ShelfAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String
    private lateinit var btnHome: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnShelf: ImageButton
    private lateinit var btnProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewShelf)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
            finish() // Remove current activity from the backstack
        }

        btnSearch.setOnClickListener {
            startActivity(Intent(this, MainActivity5::class.java))
            finish()
        }

        btnShelf.setOnClickListener {
            startActivity(Intent(this, MainActivity6::class.java))
            finish()
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, MainActivity7::class.java))
            finish()
        }
        // Initialize Adapter
        adapter = ShelfAdapter(borrowedItems) { item ->
            handleReturn(item)
        }
        recyclerView.adapter = adapter

        // Get logged-in user ID
        userId = "userIdPlaceholder" // Replace with actual user ID logic

        // Reference Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("User/$userId")

        // Fetch borrowed items
        fetchBorrowedItems()
    }

    private fun fetchBorrowedItems() {
        borrowedItems.clear()

        // Fetch borrowed books
        val borrowedBooksRef = FirebaseDatabase.getInstance().getReference("User/$userId/borrowedBooks")
        borrowedBooksRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (bookIdSnapshot in snapshot.children) {
                    val bookId = bookIdSnapshot.key
                    if (bookId != null) {
                        fetchBookDetails(bookId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity6, "Failed to load borrowed books: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch borrowed magazines
        val borrowedMagazinesRef = FirebaseDatabase.getInstance().getReference("User/$userId/borrowedMagazines")
        borrowedMagazinesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (magazineIdSnapshot in snapshot.children) {
                    val magazineId = magazineIdSnapshot.key
                    if (magazineId != null) {
                        fetchMagazineDetails(magazineId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity6, "Failed to load borrowed magazines: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchBookDetails(bookId: String) {
        val bookRef = FirebaseDatabase.getInstance().getReference("Books/$bookId")
        bookRef.get().addOnSuccessListener { snapshot ->
            val book = snapshot.getValue(BorrowedBook::class.java)
            if (book != null) {
                borrowedItems.add(ShelfItem.BookItem(book))
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun fetchMagazineDetails(magazineId: String) {
        val magazinesRef = FirebaseDatabase.getInstance().getReference("Magazine/$magazineId")
        magazinesRef.get().addOnSuccessListener { snapshot ->
            val magazine = snapshot.getValue(BorrowedMagazine::class.java)
            if (magazine != null) {
                borrowedItems.add(ShelfItem.MagazineItem(magazine))
                adapter.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load magazine details: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleReturn(item: ShelfItem) {
        when (item) {
            is ShelfItem.BookItem -> returnBook(item.book)
            is ShelfItem.MagazineItem -> returnMagazine(item.magazine)
        }
    }

    private fun returnBook(book: BorrowedBook) {
        val bookId = book.bookId
        val bookRef = FirebaseDatabase.getInstance().getReference("Books/$bookId")
        val userBooksRef = databaseReference.child("borrowedBooks/$bookId")

        bookRef.child("isAvailable").setValue(true)
        userBooksRef.removeValue().addOnSuccessListener {
            borrowedItems.remove(ShelfItem.BookItem(book))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Book returned successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun returnMagazine(magazine: BorrowedMagazine) {
        val magazineId = magazine.magazineId
        val magazineRef = FirebaseDatabase.getInstance().getReference("Magazines/$magazineId")
        val userMagazinesRef = databaseReference.child("borrowedMagazines/$magazineId")

        magazineRef.child("isAvailable").setValue(true)
        userMagazinesRef.removeValue().addOnSuccessListener {
            borrowedItems.remove(ShelfItem.MagazineItem(magazine))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Magazine returned successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}
