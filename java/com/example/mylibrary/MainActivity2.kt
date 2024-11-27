package com.example.mylibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity2 : AppCompatActivity() {
    //initialize all component
    //var = variable
    private lateinit var btnSignUp : Button
    private lateinit var btnLog : Button
    private lateinit var email: EditText
    private lateinit var password : EditText

    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //declare all component
        btnSignUp = findViewById<Button>(R.id.btnRegister)
        btnLog = findViewById(R.id.btnLogin)
        email = findViewById(R.id.eTNameLogin)
        password = findViewById(R.id.eTPasswordLogin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("User")

        //function for button register
        //val = static variable
        //Intent = function for linking to other page/activity
        btnSignUp.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btnLog.setOnClickListener{
            val email = email.text.toString()
            val password = password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty())
            {
                logIn(email, password)
            } else {
                Toast.makeText(this@MainActivity2, "All fields are mandatory", Toast.LENGTH_LONG).show()
            }
        }
    }

    //create the function login
    //this function read data to firebase
    //p = password
    //e = email
    private fun logIn(username: String, password: String)
    {
        databaseReference.orderByChild( "userName").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (custSnapshot in dataSnapshot.children){
                        val model = custSnapshot.getValue(Model::class.java)

                        if (model != null && model.userPassword== password){
                            Toast.makeText(this@MainActivity2, "Login Successful", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@MainActivity2, MainActivity3::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@MainActivity2, "Login Failed", Toast.LENGTH_LONG).show()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity2, "Database Error: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}