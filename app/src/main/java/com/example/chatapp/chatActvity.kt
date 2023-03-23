package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActvity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDbref:DatabaseReference

    var receiveRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_actvity)
        val name = intent.getStringExtra("name")
        val receiveUid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid

        mDbref=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveUid+senderUid
        receiveRoom=senderUid+receiveUid
        supportActionBar?.title=name
        chatRecyclerView=findViewById(R.id.chatRecyclerview)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sendbutton )
        messageList= ArrayList()
        messageAdapter= MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter
        //adding messages to recyclerview

        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })




        //adding message to database
        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject= Message(message, senderUid.toString())
            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiveRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
        }




    }
}