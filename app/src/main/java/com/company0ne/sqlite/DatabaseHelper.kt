package com.company0ne.sqlite


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDataBase"
        private const val TABLE_NAME = "Users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_AGE = "age"
        private const val KEY_PHONE = "phone"
        private const val KEY_EMAIL = "email"
    }

    //create query
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_QUERY =
            ("CREATE TABLE " + TABLE_NAME
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NAME + " TEXT,"
                    + KEY_AGE + " INTEGER,"
                    + KEY_PHONE + " TEXT,"
                    + KEY_EMAIL + " TEXT" + ")")

        db?.execSQL(CREATE_TABLE_QUERY)
    }
    //if we added, removed, or changed the type of a column,
    // we would need to update your database to reflect these changes.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, user.name)
        contentValues.put(KEY_AGE, user.age)
        contentValues.put(KEY_PHONE, user.phone)
        contentValues.put(KEY_EMAIL, user.email)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success

    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, user.name)
        contentValues.put(KEY_AGE, user.age)
        contentValues.put(KEY_PHONE, user.phone)
        contentValues.put(KEY_EMAIL, user.email)

        // updating row
        val success = db.update(TABLE_NAME, contentValues, KEY_ID + "=" + user.id, null)
        db.close()
        return success
    }

    //delete operation
    fun deleteUser(user: User): Int {
        val db = this.writableDatabase
        // delete user row
        val success = db.delete(TABLE_NAME, KEY_ID + "=" + user.id, null)
        db.close()
        return success
    }
    //for single recent delete user
    fun deleteUser(name: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$KEY_NAME=?", arrayOf(name))
    }

    //for delete all records
    fun deleteAllUsers(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, null, null)
        db.close()
        return success
    }

    fun getAllUsers(): List<User> {
        //: This initializes an empty of ArrayList of User
        val userList = ArrayList<User>()
        //query that retrieves all the records from TABLE_NAME
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        //opens the database for reading.
        val db = this.readableDatabase
        //This executes the SQL query and returns a Cursor object.
        // The Cursor represents the result set from the SQL query.
        val cursor = db.rawQuery(selectQuery, null)
        // moves the Cursor to the first row in the result set.
        // If the result set is empty (i.e., no users were found), moveToFirst() returns false and the body of the if statement is skipped.
        if (cursor.moveToFirst()) {
            // loop, we traverse each row in the result set.
            do {
                try {
                    //Cursor object represents the result set from a SQL query.
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AGE))
                    val phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
                    val email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                    userList.add(User(id, name, age, phone, email))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return userList

        //another way to getAllUsers-
        /*
        fun getAllUsers(): List<User> {
    // Creating a list to hold the user data
    val userList = ArrayList<User>()

    // Creating an SQL query to select all users
    val selectQuery = "SELECT * FROM $TABLE_NAME"

    // Getting an instance of the database
    val db = this.readableDatabase

    // Executing the query and getting a cursor to the results
    val cursor = db.rawQuery(selectQuery, null)

    // If the cursor is not empty
    if (cursor.moveToFirst()) {
        // Loop through the results
        do {
            // Get each attribute of the user from the cursor
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AGE))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))

            // Create a User object with the retrieved data
            val user = User(id, name, age, phone, email)

            // Add the user to the user list
            userList.add(user)
        } while (cursor.moveToNext()) // Move to the next result
    }

    // Close the cursor and the database connection
    cursor.close()
    db.close()

    // Return the list of users
    return userList
}

         */
    }
}