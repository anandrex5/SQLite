package com.company0ne.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val textView: TextView = findViewById(R.id.textView)
        //gets the list of users that were passed to the activity through as Intent.
        //retrieve Serializable from intent in the ArrayList
        val users: ArrayList<User> = intent.getSerializableExtra("users") as ArrayList<User>
        //iterates over each user in the users
        users.forEach {
            textView.append("${it.name}, ${it.age}, ${it.phone}, ${it.email}\n")
        }
    }
}







