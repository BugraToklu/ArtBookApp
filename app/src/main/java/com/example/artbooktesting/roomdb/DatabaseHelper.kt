package com.example.artbooktesting.roomdb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.artbooktesting.model.User


class DatabaseHelper
    (context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    private val USER_TABLE =
        ("CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")")


    private val DROP_USER_TABLE =
        "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(USER_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL(DROP_USER_TABLE)

        onCreate(db)
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.username)
        values.put(COLUMN_USER_PASSWORD, user.password)

        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun checkUser(username: String): Boolean {
        val columns = arrayOf(
            COLUMN_USER_NAME
        )
        val db = this.readableDatabase

        val selection = "$COLUMN_USER_NAME = ?"

        val selectionArgs = arrayOf(username)

        val cursor: Cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    fun checkUser(username: String, password: String): Boolean {
        val columns = arrayOf(
            COLUMN_USER_NAME
        )
        val db = this.readableDatabase
        val selection =
            "$COLUMN_USER_NAME = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor: Cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserManager.db"
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_PASSWORD = "user_password"
    }
}