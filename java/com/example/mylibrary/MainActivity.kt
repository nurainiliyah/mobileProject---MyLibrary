package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    //declare to connect with database
    private lateinit var dbRef: DatabaseReference
    //initial all component
    private lateinit var submit: Button
    private lateinit var reset: Button
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var alreadyhaveaccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //declare all component
        submit = findViewById(R.id.btnSubmit)
        reset = findViewById(R.id.btnReset)
        name = findViewById(R.id.eTName)
        password = findViewById(R.id.eTPassword)
        phone = findViewById(R.id.eTPhone)
        email = findViewById(R.id.eTEmail)
        alreadyhaveaccount = findViewById(R.id.alreadyhaveanaccount)

        //popup massage when click button add record
        Toast.makeText(this, "Submit", Toast.LENGTH_LONG).show()
        submit.setOnClickListener{
            //call function saveEmployeeData
            //parameter - change the input data to string
            saveData(email.text.toString(),name.text.toString(),
                password.text.toString(), phone.text.toString())
        }

        reset.setOnClickListener{
            name.setText("")
            password.setText("")
            phone.setText("")
            email.setText("")
        }

        alreadyhaveaccount.setOnClickListener{
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }

    //create the function saveData
    //this function send data to firebase
    //n = name
    //p = password
    //e = email
    //t = phone
    private fun saveData(e: String, n: String, p: String, t: String)
    {
        //getInstance = get object
        //Customer refer to table
        //Customer can change to other name
        //link database named Customer
        dbRef = FirebaseDatabase.getInstance().getReference("User")
        //produce auto generate customer id
        //!! refer must had record or id cannot null
        val userId = dbRef.push().key!!

        //customer is a object
        //push the data to database
        //customerId will autogenerate
        //data will output by user
        //input name, password, phone, email
        val em = Model(e, userId, n, p, t)

        //setting to push data inside table
        dbRef.child(userId).setValue(em)

        //success record, it will popup success
            .addOnCompleteListener{
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                //fail to record. it will popup failure
            }.addOnFailureListener{
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }
        //declare variable i to connect to next pages/activity
        val i = Intent(this, MainActivity2::class.java)
        startActivity(i)
    }
}