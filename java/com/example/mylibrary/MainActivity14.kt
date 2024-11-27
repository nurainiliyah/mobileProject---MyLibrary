package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import android.widget.ImageButton


class MainActivity14 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var magazines: ArrayList<Magazine>
    private lateinit var adapter: MagazineAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var btnHome: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnShelf: ImageButton
    private lateinit var btnProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main14)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMagazines)
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

        // Initialize data structures
        magazines = ArrayList()
        adapter = MagazineAdapter(magazines) { magazine ->
            // Handle magazine click
            val intent = Intent(this, MainActivity16::class.java)
            intent.putExtra("MAGAZINE_ID", magazine.magazineId)
            intent.putExtra("USER_ID", getCurrentUserId()) // Pass logged-in user's ID
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Reference Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Magazine")

        // Fetch magazine data
        fetchMagazines()
    }

    private fun fetchMagazines() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                magazines.clear() // Clear old data
                for (magazineSnapshot in snapshot.children) {
                    val magazine = magazineSnapshot.getValue(Magazine::class.java)
                    if (magazine != null) {
                        magazines.add(magazine)
                    }
                }
                adapter.notifyDataSetChanged() // Update RecyclerView
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity14,
                    "Failed to load magazines: ${error.message}",
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
