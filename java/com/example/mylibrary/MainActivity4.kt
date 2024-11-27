package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity4 : AppCompatActivity() {

    private lateinit var btnHome : ImageButton
    private lateinit var btnSearch : ImageButton
    private lateinit var btnShelf : ImageButton
    private lateinit var btnProfile : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)

        val constraintKids = findViewById<ConstraintLayout>(R.id.constraintKids)

        constraintKids.setOnClickListener{
            val i = Intent (this, MainActivity8::class.java)
            startActivity(i)
        }

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