package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import android.widget.ImageButton

class MainActivity15 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<Books>
    private lateinit var adapter: BookAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var btnHome: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnShelf: ImageButton
    private lateinit var btnProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main15)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)

        // Initialize data structures
        bookList = ArrayList()

        // Initialize Adapter with Click Listener
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
        adapter = BookAdapter(bookList) { selectedBook ->
            // On book click, navigate to BookDetailsActivity
            val intent = Intent(this, MainActivity9::class.java)
            intent.putExtra("BOOK_ID", selectedBook.bookId)
            intent.putExtra("USER_ID", getCurrentUserId()) // Pass logged-in user's ID
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Reference Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Books")

        // Fetch books from Firebase
        fetchBooks()
    }

    private fun fetchBooks() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear() // Clear old data
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(Books::class.java)
                    if (book != null) {
                        bookList.add(book) // Add each book to the list
                    }
                }
                adapter.notifyDataSetChanged() // Notify adapter of data change
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity15,
                    "Failed to load books: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun getCurrentUserId(): String {
        // Replace with your actual logic to get the logged-in user's ID
        return "userIdPlaceholder"
    }
}
