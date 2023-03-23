package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userrecyclerview:RecyclerView
    private lateinit var userlist:ArrayList<user>
    private lateinit var adapter:userAdapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mdref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        mdref= FirebaseDatabase.getInstance().getReference()
        userlist=ArrayList()
        adapter= userAdapter(this, userlist)
        userrecyclerview=findViewById(R.id.userRecyclerView)
        userrecyclerview.layoutManager=LinearLayoutManager(this)
        userrecyclerview.adapter=adapter
        mdref.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser=postSnapshot.getValue((user::class.java))
                    if(mAuth.currentUser?.uid!=currentUser?.uid){
                    userlist.add(currentUser!!)}
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.log_out)
        {
            mAuth.signOut()
            startActivity(Intent(this@MainActivity, loginActivity::class.java))
            finish()
            return true
        }
        return true
    }
}