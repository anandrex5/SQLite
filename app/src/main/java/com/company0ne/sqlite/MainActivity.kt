package com.company0ne.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editAge: EditText
    private lateinit var editPhone: EditText
    private lateinit var editEmail: EditText
    private lateinit var saveButton: Button
    private lateinit var showButton: Button
    private lateinit var deleteButton: Button
    private lateinit var buttonDeleteAll: Button
    private lateinit var dbHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editName = findViewById(R.id.editTextName)
        editAge = findViewById(R.id.editTextAge)
        editPhone = findViewById(R.id.editTextPhone)
        editEmail = findViewById(R.id.editTextEmail)
        saveButton = findViewById(R.id.buttonSave)
        showButton = findViewById(R.id.buttonShow)
        deleteButton = findViewById(R.id.buttonDelete)
        buttonDeleteAll = findViewById(R.id.buttonDeleteAll)

        //handle dataBase
        dbHandler = DatabaseHelper(this)

        saveButton.setOnClickListener {
            //age are in int So,
            val age = editAge.text.toString().toIntOrNull() ?: 0

            val user = User(
                0, editName.text.toString(),
                age, editPhone.text.toString(),
                editEmail.text.toString()
            )
            val result = dbHandler.addUser(user)
            Toast.makeText(this, if (result > 0) "User saved" else "Save failed", Toast.LENGTH_SHORT).show()
        }
        showButton.setOnClickListener {
            val users = dbHandler.getAllUsers()
            //to print in locat
            // users.forEach {
            // println("User Details: $it")
            val intent = Intent(this, DisplayActivity::class.java)
            intent.putExtra("users", ArrayList(users))
            startActivity(intent)
        }
        deleteButton.setOnClickListener {
            val name = editName.text.toString()
            if (name.isNotEmpty()) {
                val result = dbHandler.deleteUser(name)
                Toast.makeText(this, if (result > 0) "User deleted" else "Delete failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
            //for delete all users data
            buttonDeleteAll.setOnClickListener {
                val result = dbHandler.deleteAllUsers()

                Toast.makeText(this, if (result > 0) "All users deleted" else "No users to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
