package com.itsi.almuntaqimorevn.model.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BookmarkDbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DUA_ID_COL + " INTEGER" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addDuaId(duaId : Int ): Long {

        // below we are creating a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair values.put(NAME_COl, name)
        values.put(DUA_ID_COL, duaId)

        // here we are creating a writable variable of
        // our database as we want to insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        val rowId = db.insert(TABLE_NAME, null, values)

        // at last we are closing our database
        db.close()

        return rowId
    }

    // below method is to get
    // all data from our database
    fun getDuaIds(): ArrayList<Int> {

        // here we are creating a readable
        // variable of our database as we want to read value from it
        val db = this.readableDatabase

        val arrayList: ArrayList<Int> = ArrayList<Int>()
        //Cursor res = db.rawQuery( "select * from "+CONTACTS_TABLE_NAME, null );

        // below code returns a cursor to read data from the database
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor.moveToFirst()
        while(!cursor.isAfterLast) {
            arrayList.add(cursor.getInt(cursor.getColumnIndexOrThrow(DUA_ID_COL)))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return arrayList
    }

    fun checkDuaIdExists(duaId: Int): Boolean {
        val db = this.readableDatabase

        val cursor = db.query(TABLE_NAME, null, "$DUA_ID_COL = ? ", arrayOf<String> (duaId.toString()),
            null, null, null)

        return if (cursor.moveToFirst()) {
            val columnDuaId = cursor.getInt(cursor.getColumnIndexOrThrow(DUA_ID_COL))
            Log.d("DB", "Dua ID exists $columnDuaId")
            cursor.close()
            db.close()
            true
        } else {
            cursor.close()
            db.close()
            false
        }
    }

    fun removeDuaId(duaId: Int) {
        val db = this.writableDatabase

        val noRowsDel = db.delete(TABLE_NAME, "$DUA_ID_COL = ?", arrayOf<String> (duaId.toString()))
        Log.d("DB", "No of rows deleted = $noRowsDel")
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private const val DATABASE_NAME = "ITSINDONESIA_BKM_DB"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        const val TABLE_NAME = "bookmarks_table"

        // below is the variable for id column
        const val ID_COL = "id"

        const val DUA_ID_COL = "duaid"
        // below is the variable for name column
        //val NAME_COl = "name"

        // below is the variable for age column
        //val AGE_COL = "age"
    }
}
