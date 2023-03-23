package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class loginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        var username = findViewById<EditText>(R.id.usename)
        var  email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)
        var login = findViewById<Button>(R.id.loginbutton)
        var  register = findViewById<TextView>(R.id.registerform)
        mAuth = FirebaseAuth.getInstance()
        
        login.setOnClickListener {
            val user = username.text.toString()
            val emailtext = email.text.toString()
            val pass = password.text.toString()
            loginn(emailtext,pass)
            

        }
        register.setOnClickListener {

            startActivity(Intent(this@loginActivity, signUpActivity::class.java))
        }

    }

    private fun loginn(emailtext: String, pass: String) {
        mAuth.signInWithEmailAndPassword(emailtext, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    finish()
                    startActivity(Intent(this@loginActivity, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"no such user",Toast.LENGTH_SHORT).show()
                }
            }
    }


}