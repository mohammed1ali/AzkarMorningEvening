package com.itsi.almuntaqimorevn.primarylist

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.itsi.almuntaqimorevn.R
import com.itsi.almuntaqimorevn.model.DuaEvidence
import com.itsi.almuntaqimorevn.utils.MyUtils
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
            val duaStr = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(i) +" - "+ oneDuaEvd.mDua
            //parseAndMakeNabiSpeechRed(oneDuaEvd.mDua);
            holder.textViewDua.text = HtmlCompat.fromHtml(duaStr, HtmlCompat.FROM_HTML_MODE_LEGACY)
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

    /***
     * TODO: Make logic for spanning with span array to show Nabi's speech in red
     */
    private fun parseAndMakeNabiSpeechRed(duaStr: String) {
        var duaForSpan = arrayOf<String>()
        var startRedText = false
        var spanIndex = 0
        var strWithoutSpan = ""
        for (i in 0 until duaStr.length) {
            //println(str[i])
            if (duaStr[i].equals("r")) {

            } else if (duaStr[i].equals("e")) {

            } else {

            }

        }
        duaStr.forEach {
            if (it.equals("r")) {
                startRedText = true
            }
            if (it.equals("e")) {
                startRedText = false
            }
            //tempStr[it.]
            if (startRedText) {

            }
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