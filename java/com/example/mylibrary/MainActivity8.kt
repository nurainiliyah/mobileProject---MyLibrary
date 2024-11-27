package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity8 : AppCompatActivity() {

    private lateinit var btnHome : ImageButton
    private lateinit var btnSearch : ImageButton
    private lateinit var btnShelf : ImageButton
    private lateinit var btnProfile : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)

        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)

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
    }
}