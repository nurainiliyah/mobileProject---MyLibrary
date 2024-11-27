package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity7 : AppCompatActivity() {

    private lateinit var btnHome: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnShelf: ImageButton
    private lateinit var btnProfile: ImageButton
    private lateinit var usernameTextView: TextView // Ensure this matches your layout ID for displaying the username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        // Initialize UI components
        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)
        usernameTextView = findViewById(R.id.name)

        // Add navigation button actions
        btnHome.setOnClickListener {
            val i = Intent(this, MainActivity3::class.java)
            startActivity(i)
        }
        btnSearch.setOnClickListener {
            val i = Intent(this, MainActivity5::class.java)
            startActivity(i)
        }
        btnShelf.setOnClickListener {
            val i = Intent(this, MainActivity6::class.java)
            startActivity(i)
        }
        btnProfile.setOnClickListener {
            val i = Intent(this, MainActivity7::class.java)
            startActivity(i)
        }

        // Fetch user details
        val userId = "YourActualUserId" // Replace this with the correct user ID logic
        fetchUserDetails(userId)
    }

    // Fetch user details from Firebase
    private fun fetchUserDetails(userId: String) {
        // Log the userId to ensure it's correct
        Log.d("FirebaseDebug", "Fetching details for userId: $userId")

        val userRef = FirebaseDatabase.getInstance().getReference("User").child(userId)

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // Safely retrieve the userName field
                val username = snapshot.child("userName").value?.toString() ?: "Unknown User"
                Log.d("FirebaseDebug", "Fetched username: $username")
                usernameTextView.text = username // Display the username in the TextView
            } else {
                Log.d("FirebaseDebug", "User not found for ID: $userId")
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("FirebaseDebug", "Error: ${it.message}")
            Toast.makeText(this, "Failed to load user details: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
