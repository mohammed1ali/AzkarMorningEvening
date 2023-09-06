package com.itsi.almuntaqimorevn.model

import android.util.Log
import org.json.JSONArray

class DuaDb {

    fun getChapterEvd(): ArrayList<ChapterEvidence> {
        //val strjson = fetchJsonData(context)
        val chNamesList:ArrayList<String> = ArrayList<String>()
        val chEvdList:ArrayList<ChapterEvidence> = ArrayList<ChapterEvidence>()
        //Decode from string
        //val employeeObject = Json.decodeFromString<String>(strjson);
        //println(employeeObject);

        val bookJsonArray = JSONArray(DuaData.hardcodedJsonDataAsString)
        Log.i("JSON", bookJsonArray.toString())
        //val jsonArray = JSONTokener(strjson).nextValue() as JSONArray
        for (i in 0 until bookJsonArray.length()) {
            val oneChObj = bookJsonArray.getJSONObject(i)
            val oneChEvd = ChapterEvidence(oneChObj.getInt("ch_id"), oneChObj.getString("ch_name"),
                                oneChObj.getString("ch_evidence"))
            chEvdList.add(oneChEvd)
        }
        return chEvdList
    }

    /**
     * Returns the list of duas with the respective ids and evidence for a particular chapter
     **/
    fun getDuaEvidenceList(chPos:Int): ArrayList<DuaEvidence> {
        val bookJsonArray = JSONArray(DuaData.hardcodedJsonDataAsString)
        val chName = bookJsonArray.getJSONObject(chPos).getString("ch_name")
        val oneChObject = bookJsonArray.getJSONObject(chPos)
        val duaArray = oneChObject.getJSONArray("duas")
        var duaEvidenceList = ArrayList<DuaEvidence>()

        for (i in 0 until duaArray.length()) {
            val oneDuaObj = duaArray.getJSONObject(i)
            Log.i("ID: ", oneDuaObj.toString())
            var duaEvidence = DuaEvidence(oneDuaObj.getInt("id"), oneDuaObj.getString("dua"),
                                oneDuaObj.getString("evidence"))
            duaEvidenceList.add(duaEvidence)
            //chNamesList.add(chName)
        }
        return duaEvidenceList
    }

    fun getChapterName(chPos: Int): String {
        val bookJsonArray = JSONArray(DuaData.hardcodedJsonDataAsString)
        return bookJsonArray.getJSONObject(chPos).getString("ch_name")
    }
}