package com.itsi.almuntaqimorevn.primarylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsi.almuntaqimorevn.R

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.header_item, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        //holder.bind(flowerCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }



    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvHeader: TextView = itemView
            .findViewById(R.id.tv_header_txt)

        /*fun bind(flowerCount: Int) {
            //flowerNumberTextView.text = flowerCount.toString()
        }*/
    }
}