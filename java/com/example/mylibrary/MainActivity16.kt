package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide
import android.widget.ImageView

class MainActivity16 : AppCompatActivity() {

    private lateinit var btnHome : ImageButton
    private lateinit var btnSearch : ImageButton
    private lateinit var btnShelf : ImageButton
    private lateinit var btnProfile : ImageButton
    private lateinit var btnBorrow: Button
    private lateinit var magazineTitle: TextView
    private lateinit var magazineDescription: TextView
    private lateinit var ivMagazineCover: ImageView
    private lateinit var magazineGenre: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)
        btnBorrow = findViewById(R.id.btnBorrow)
        magazineTitle = findViewById(R.id.magazineTitle)
        magazineDescription = findViewById(R.id.magazineDescription)
        ivMagazineCover = findViewById(R.id.ivMagazineCover)
        magazineGenre = findViewById(R.id.magazineGenre)

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

        val magazineId = intent.getStringExtra("MAGAZINE_ID")
        val userId = intent.getStringExtra("USER_ID")
        if (userId != null && magazineId != null) {
            fetchMagazineDetails(magazineId)

            btnBorrow.setOnClickListener {
                borrowMagazine(magazineId, userId)
            }
        } else {
            Toast.makeText(this, "Error loading details", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    private fun fetchMagazineDetails(magazineId: String) {
        val magazineRef = FirebaseDatabase.getInstance().getReference("Magazine").child(magazineId)
        magazineRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val title = snapshot.child("title").value.toString()
                val description = snapshot.child("magazineDescription").value.toString()
                val isAvailable = snapshot.child("isAvailable").value as Boolean
                val coverImageUrl = snapshot.child("coverImageUrl").value.toString()
                val genre = snapshot.child("genre").value.toString()

                // Update UI
                magazineTitle.text = title
                magazineDescription.text = description
                btnBorrow.isEnabled = isAvailable
                magazineGenre.text = genre


                if (!isAvailable) {
                    btnBorrow.text = "Not Available"
                }
                Glide.with(this)
                    .load(coverImageUrl) // Pass the URL of the image
                    .placeholder(R.drawable.placeholder) // Optional: placeholder image
                    .error(R.drawable.error) // Optional: error image if the URL is invalid
                    .into(ivMagazineCover) // Target ImageView
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load magazine details", Toast.LENGTH_SHORT).show()
        }
    }



    private fun borrowMagazine(magazineId: String, userId: String) {
        val magazineRef = FirebaseDatabase.getInstance().getReference("Magazine").child(magazineId)
        val userRef = FirebaseDatabase.getInstance().getReference("User").child(userId)

        magazineRef.child("isAvailable").setValue(false)
        magazineRef.child("borrower").setValue(userId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userRef.child("borrowedMagazines").child(magazineId).setValue(true).addOnCompleteListener {
                    Toast.makeText(this, "Magazine borrowed successfully!", Toast.LENGTH_SHORT).show()
                    btnBorrow.isEnabled = false
                    btnBorrow.text = "Not Available"
                }
            } else {
                Toast.makeText(this, "Failed to borrow magazine!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}