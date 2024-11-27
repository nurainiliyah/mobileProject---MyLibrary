package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide

class MainActivity9 : AppCompatActivity() {

    private lateinit var btnHome : ImageButton
    private lateinit var btnSearch : ImageButton
    private lateinit var btnShelf : ImageButton
    private lateinit var btnProfile : ImageButton
    private lateinit var btnBorrow: Button
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookDescription: TextView
    private lateinit var ivBookCover: ImageView
    private lateinit var bookGenre : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)
        btnBorrow = findViewById(R.id.btnBorrow)
        bookTitle = findViewById(R.id.bookTitle)
        bookAuthor = findViewById(R.id.bookAuthor)
        bookDescription = findViewById(R.id.bookDescription)
        ivBookCover = findViewById(R.id.ivBookCover)
        bookGenre = findViewById(R.id.magazineGenre)

        btnHome.setOnClickListener{
            val i = Intent (this, MainActivity3::class.java)
            startActivity(i)
        }
        btnSearch.setOnClickListener{
            val i = Intent (this, MainActivity5::class.java)
            startActivity(i)
        }

        btnShelf.setOnClickListener{
            val i = Intent (this, MainActivity6::class.java)
            startActivity(i)
        }

        btnProfile.setOnClickListener{
            val i = Intent (this, MainActivity7::class.java)
            startActivity(i)
        }

        val bookId = intent.getStringExtra("BOOK_ID")
        val userId = intent.getStringExtra("USER_ID") // The logged-in user's ID

        if (bookId != null && userId != null) {
            loadBookDetails(bookId)

            // Set up borrow button click listener
            btnBorrow.setOnClickListener {
                borrowBook(bookId, userId)
            }
        } else {
            Toast.makeText(this, "Error loading book details", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadBookDetails(bookId: String) {
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)

        bookRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val title = snapshot.child("title").value.toString()
                val author = snapshot.child("author").value.toString()
                val description = snapshot.child("description").value.toString()
                val isAvailable = snapshot.child("isAvailable").value as Boolean
                val imageUrl = snapshot.child("imageUrl").value.toString()
                val genre = snapshot.child("genre").value.toString()

                // Update UI
                bookTitle.text = title
                bookAuthor.text = author
                bookDescription.text = description
                btnBorrow.isEnabled = isAvailable
                bookGenre.text = genre

                if (!isAvailable) {
                    btnBorrow.text = "Not Available"
                }
                Glide.with(this)
                    .load(imageUrl) // Pass the URL of the image
                    .placeholder(R.drawable.placeholder) // Optional: placeholder image
                    .error(R.drawable.error) // Optional: error image if the URL is invalid
                    .into(ivBookCover) // Target ImageView
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load book details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun borrowBook(bookId: String, userId: String) {
        val bookRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val userRef = FirebaseDatabase.getInstance().getReference("User").child(userId)

        bookRef.child("isAvailable").setValue(false)
        bookRef.child("borrower").setValue(userId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userRef.child("borrowedBooks").child(bookId).setValue(true).addOnCompleteListener {
                    Toast.makeText(this, "Book borrowed successfully!", Toast.LENGTH_SHORT).show()
                    btnBorrow.isEnabled = false
                    btnBorrow.text = "Not Available"
                }
            } else {
                Toast.makeText(this, "Failed to borrow book!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}