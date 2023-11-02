package com.itsi.almuntaqimorevn.utils

import android.content.Context
import android.util.Log
import com.itsi.almuntaqimorevn.model.db.BookmarkDbHelper
import java.text.FieldPosition

class BkmUtils {
    fun saveBookmark(id: Int, context: Context) {

        // below we have created
        // a new DBHelper class,
        // and passed context to it
        val db = BookmarkDbHelper(context, null)

        // calling method to add
        // name to our database
        val rowId = db.addDuaId(id)
        Log.d("DB", "rowId = $rowId")
    }

    fun getAllBookmarks(context: Context): ArrayList<Int> {
        val db = BookmarkDbHelper(context, null)
        return db.getDuaIds()
    }

    fun removeBookmark(id: Int, context: Context) {
        val db = BookmarkDbHelper(context, null)
        db.removeDuaId(id)
    }


}
