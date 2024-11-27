package com.example.mylibrary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView

class MainActivity5 : AppCompatActivity() {

    private lateinit var btnHome: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnShelf: ImageButton
    private lateinit var btnProfile: ImageButton
    private lateinit var btnBooks: Button
    private lateinit var btnMagazines: Button

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        btnHome = findViewById(R.id.imageBlibrary)
        btnSearch = findViewById(R.id.imageBSearch)
        btnShelf = findViewById(R.id.imageBShelf)
        btnProfile = findViewById(R.id.imageBProfile)
        btnBooks = findViewById(R.id.btnBooks)
        btnMagazines = findViewById(R.id.btnMagazines)

        btnBooks.setOnClickListener{
            startActivity(Intent(this, MainActivity15::class.java))
            finish()
        }

        btnMagazines.setOnClickListener {
            startActivity(Intent(this, MainActivity14::class.java))
            finish()
        }
        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
            finish() // Remove current activity from the backstack
        }

        btnSearch.setOnClickListener {
            // Do nothing or show a toast since we're already on the search page
        }

        btnShelf.setOnClickListener {
            startActivity(Intent(this, MainActivity6::class.java))
            finish()
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, MainActivity7::class.java))
            finish()
        }
    }
}
