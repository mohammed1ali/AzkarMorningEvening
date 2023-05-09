package com.itsi.assahihalmuntaqi.model

import android.content.Context
import android.util.Log
import com.itsi.assahihalmuntaqi.R
import org.json.JSONArray
import org.json.JSONTokener
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.json.JSONObject

class DuaDb {
    //val usersArray
    val jsonBook = ""
    fun fetchJsonData(context: Context): String {
        val strjson = context.getString(R.string.jsonData)
        return strjson;
    }

    fun getChapterNames(): ArrayList<String> {
        //val strjson = fetchJsonData(context)
        val chNamesList:ArrayList<String> = ArrayList<String>();
        //Decode from string
        //val employeeObject = Json.decodeFromString<String>(strjson);
        //println(employeeObject);

            val bookJsonArray = JSONArray(DuaData.hardcodedJsonDataAsString)
        Log.i("JSON", bookJsonArray.toString())
        //val jsonArray = JSONTokener(strjson).nextValue() as JSONArray
        for (i in 0 until bookJsonArray.length()) {
            val chName = bookJsonArray.getJSONObject(i).getString("ch_name")
            Log.i("ID: ", chName)
            chNamesList.add(chName)
        }
        return chNamesList
    }
}