package com.itsi.assahihalmuntaqi.primarylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsi.assahihalmuntaqi.R
import com.itsi.assahihalmuntaqi.model.ChapterEvidence
import com.itsi.assahihalmuntaqi.model.DuaEvidence

class DuaEvdAdapter (private val mDuaEvdList: ArrayList<DuaEvidence>) : RecyclerView.Adapter<DuaEvdAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_dua_evd, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneDuaEvd = mDuaEvdList[position]
        holder.textViewDua.text = oneDuaEvd.mDua
        holder.textViewEvd.text = oneDuaEvd.mEvidence
    }

    override fun getItemCount(): Int {
        return mDuaEvdList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewDua: TextView = itemView.findViewById(R.id.tv_list_dua)
        val textViewEvd: TextView = itemView.findViewById(R.id.tv_list_evd)
    }
}