package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.RecyclerView.*
import com.google.firebase.auth.FirebaseAuth

class userAdapter(val context: Context, val userlist: ArrayList<user>): RecyclerView.Adapter<userAdapter.userviewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userviewholder {
         val view:View=LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return  userviewholder(view)
    }

    override fun onBindViewHolder(holder: userviewholder, position: Int) {
         val currentUser=userlist[position]
        holder.user.text=currentUser.user
        holder.itemView.setOnClickListener{
            val intent=Intent(context,chatActvity::class.java)
            intent.putExtra("name",currentUser.user)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
         return userlist.size
    }
    class userviewholder(itemView: View): ViewHolder(itemView){
          val user=itemView.findViewById<TextView>(R.id.Username_id)
    }


}