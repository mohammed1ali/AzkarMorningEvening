package com.itsi.almuntaqimorevn.primarylist
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsi.almuntaqimorevn.R
import com.itsi.almuntaqimorevn.model.ChapterEvidence

class PrimaryAdapter(private val mChEvdList: ArrayList<ChapterEvidence>) : RecyclerView.Adapter<PrimaryAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val oneChEvd = mChEvdList[position]
        holder.textView.text = oneChEvd.mChapterName
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, oneChEvd.mChapterName)
            }
        }
    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, chName: String)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mChEvdList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}