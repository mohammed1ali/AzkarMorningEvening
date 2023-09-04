package com.itsi.assahihalmuntaqi.primarylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.itsi.assahihalmuntaqi.R
import com.itsi.assahihalmuntaqi.model.ChapterEvidence
import com.itsi.assahihalmuntaqi.model.DuaEvidence
import com.itsi.assahihalmuntaqi.utils.MyUtils
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.Locale

class DuaEvdAdapter (private val mDuaEvdList: ArrayList<DuaEvidence>) : RecyclerView.Adapter<DuaEvdAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_dua_evd, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneDuaEvd = mDuaEvdList[position]

        if (itemCount==1)
            holder.textViewDua.text = oneDuaEvd.mDua
        else
        {
            val i = position+1
            val str = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(i) +" - "+ oneDuaEvd.mDua
            holder.textViewDua.text = str
        }
        if(!oneDuaEvd.mEvidence.equals("")) {
            holder.textViewEvd.visibility = View.VISIBLE
            holder.viewDividerDuaEvd.visibility = View.VISIBLE
            holder.textViewEvd.text = oneDuaEvd.mEvidence

            val param = holder.textViewDua.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0, MyUtils()
                .convertDpToPixel(8.0f, holder.textViewDua.context).toInt())
            holder.textViewDua.layoutParams = param

        }
        else {
            holder.textViewEvd.visibility = View.GONE
            holder.viewDividerDuaEvd.visibility = View.GONE

            val param = holder.textViewDua.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.textViewDua.layoutParams = param
        }
    }

    override fun getItemCount(): Int {
        return mDuaEvdList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewDua: TextView = itemView.findViewById(R.id.tv_list_dua)
        val textViewEvd: TextView = itemView.findViewById(R.id.tv_list_evd)
        val viewDividerDuaEvd: View = itemView.findViewById(R.id.divider_dua_evd)
    }
}