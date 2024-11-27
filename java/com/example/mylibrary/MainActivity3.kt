package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity3 : AppCompatActivity() {

    private lateinit var btnBooks : ImageButton
    private lateinit var btnMagazine : ImageButton
    private lateinit var seeall : TextView
    private lateinit var btnSearch : ImageButton
    private lateinit var btnShelf : ImageButton
    private lateinit var btnProfile : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        btnBooks = findViewById<ImageButton>(R.id.imageButtonBook)
        btnMagazine = findViewById<ImageButton>(R.id.imageButtonMagazine)
        seeall = findViewById(R.id.textVSeeAll)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)

        val constraintKids = findViewById<ConstraintLayout>(R.id.constraintkids)

        btnBooks.setOnClickListener{
            val i = Intent (this, MainActivity15::class.java)
            startActivity(i)
        }

        btnMagazine.setOnClickListener{
            val i = Intent (this, MainActivity14::class.java)
            startActivity(i)
        }


        seeall.setOnClickListener{
            val i = Intent (this, MainActivity4::class.java)
            startActivity(i)
        }

        constraintKids.setOnClickListener{
            val i = Intent (this, MainActivity8::class.java)
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