package com.example.chatapp
import android.content.Intent
import android.os.Bundle


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class signUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_up)
        var username = findViewById<EditText>(R.id.usename)
        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)
        var repassword = findViewById<EditText>(R.id.repassword)
        var register = findViewById<Button>(R.id.loginbutton)
        var login = findViewById<TextView>(R.id.lsetlogin)
        mAuth = FirebaseAuth.getInstance()
        login.setOnClickListener {

            startActivity(Intent(this@signUpActivity, loginActivity::class.java))
        }
        register.setOnClickListener {

            val user: String = username.getText().toString()
            val emailtext: String = email.getText().toString()
            val pass: String = password.getText().toString()
            val cpass: String = repassword.getText().toString()
            if(user.length>=8 && user.length<=15)
            {
                if (cpass.equals(pass)) {
                    signup(user,emailtext, pass)
                }
                else {
                    Toast.makeText(this, "password fields are not same",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this, "username atleast 8 , atmost 15 characters",Toast.LENGTH_SHORT).show()
            }



        }
    }

        fun signup(user:String,emailtext: String, pass: String) {
            mAuth.createUserWithEmailAndPassword(emailtext, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        addUserToDatabase(user,emailtext,mAuth.currentUser?.uid!!)
                        finish()
                        startActivity(Intent(this@signUpActivity, loginActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "some error occured,check email must be unique", Toast.LENGTH_SHORT).show()
                    }
                }


        }

    private fun addUserToDatabase(user: String, emailtext: String, uid: String?) {
          mdref=FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mdref.child("user").child(uid).setValue(user(user,emailtext,uid))
        }
    }

}





