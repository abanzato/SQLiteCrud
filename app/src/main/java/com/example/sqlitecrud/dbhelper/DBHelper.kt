package com.example.sqlitecrud.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitecrud.model.Guest

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER)  {

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "guestlist.db"

        // TABLE PROPERTIES
        private val TABLE_NAME = "Guest"
        private val COL_ID = "Id"
        private val COL_NAME = "Name"
        private val COL_ADDRESS = "Address"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_ADDRESS TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // CRUD
    val allGuests:ArrayList<Guest>
        get() {
            val listGuest = ArrayList<Guest>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db:SQLiteDatabase = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()) {
                do {
                    val guest = Guest(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                    )

                    listGuest.add(guest)
                }while (cursor.moveToNext())
            }
            db.close()
            return listGuest
        }

    fun addGuest(guest: Guest) {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME,guest.name)
        values.put(COL_ADDRESS,guest.address)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateGuest(guest: Guest): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, guest.id)
        values.put(COL_NAME, guest.name)
        values.put(COL_ADDRESS, guest.address)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(guest.id.toString()))
    }

    fun deleteGuest(guest: Guest) {
        val db: SQLiteDatabase = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(guest.id.toString()))
        db.close()
    }
}