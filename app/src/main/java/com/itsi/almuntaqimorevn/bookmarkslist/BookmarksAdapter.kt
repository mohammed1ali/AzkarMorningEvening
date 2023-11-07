package com.itsi.almuntaqimorevn.bookmarkslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.itsi.almuntaqimorevn.R
import com.itsi.almuntaqimorevn.model.DuaDb
import com.itsi.almuntaqimorevn.model.DuaEvidence
import com.itsi.almuntaqimorevn.utils.BkmUtils
import com.itsi.almuntaqimorevn.utils.MyUtils
import java.text.NumberFormat
import java.util.Locale

class BookmarksAdapter(private var mBookmarkedDuaEvdList: ArrayList<DuaEvidence>) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_dua_evd, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneDuaEvd = mBookmarkedDuaEvdList[position]

        if (itemCount==1) {
            holder.tvDuaNumber.text = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(oneDuaEvd.mId)
            holder.textViewDua.text = oneDuaEvd.mDua
            holder.textViewEvd.text = oneDuaEvd.mEvidence
        }
        else
        {
            val i = position+1
            val dua = MyUtils().parseAndMakeNabiSpeechRed(holder.textViewDua.context, oneDuaEvd.mDua, MyUtils.TEXT_TYPE_DUA)

            holder.tvDuaNumber.text = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(oneDuaEvd.mId)

            if (oneDuaEvd.mQuranic) {
                val typeface = ResourcesCompat.getFont(holder.textViewDua.context, com.itsi.almuntaqimorevn.R.font.uthmanic_hafs1_ver18)
                holder.textViewDua.typeface = typeface
            } else {
                val typeface = ResourcesCompat.getFont(holder.textViewDua.context, com.itsi.almuntaqimorevn.R.font.uthman)
                holder.textViewDua.typeface = typeface
            }

            holder.textViewDua.text = HtmlCompat.fromHtml(dua, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        if(!oneDuaEvd.mEvidence.equals("")) {
            holder.textViewEvd.visibility = View.VISIBLE
            holder.viewDividerDuaEvd.visibility = View.VISIBLE

            val evidence = MyUtils().parseAndMakeNabiSpeechRed(holder.textViewDua.context, oneDuaEvd.mEvidence, MyUtils.TEXT_TYPE_EVIDENCE)
            holder.textViewEvd.text = HtmlCompat.fromHtml(evidence, HtmlCompat.FROM_HTML_MODE_LEGACY)

            val param = holder.textViewDua.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0, MyUtils()
                .convertDpToPixel(8.0f, holder.textViewDua.context).toInt())
            holder.textViewDua.layoutParams = param

        } else {
            holder.textViewEvd.visibility = View.GONE
            holder.viewDividerDuaEvd.visibility = View.GONE

            val param = holder.textViewDua.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0,0,0,0)
            holder.textViewDua.layoutParams = param
        }

        holder.imgBtnOptions.setOnClickListener {

            // Show bottom dialog
            val bt = BottomSheetDialog(it.context, R.style.BottomSheetDialogTheme)
            val view: View = LayoutInflater.from(it.context).inflate(R.layout.bottom_sheet_lay, null)

            view.findViewById<TextView>(R.id.tv_btmsht_bookmark).text = it.context.getString(R.string.remove_bookmark)

            /*if(mBookmarksList?.contains(position+1) == true) {
                view.findViewById<TextView>(R.id.tv_btmsht_bookmark).text = it.context.getString(R.string.remove_bookmark)
            } else {
                view.findViewById<TextView>(R.id.tv_btmsht_bookmark).text = it.context.getString(R.string.bookmark)
            }*/

            view.findViewById<View>(R.id.tv_btmsht_share).setOnClickListener {
                bt.dismiss()

                /*This will be the actual content you wish you share.*/
                val shareBody = oneDuaEvd.mDua + "\n" + oneDuaEvd.mEvidence
                MyUtils().actionShare(shareBody, it.context)
            }

            view.findViewById<View>(R.id.tv_btmsht_bookmark).setOnClickListener {
                bt.dismiss()

                BkmUtils().removeBookmark(oneDuaEvd.mId, it.context)
                Toast.makeText(it.context, it.context.getString(R.string.bookmark_removed),
                    Toast.LENGTH_SHORT).show()
                val bookmarksList = BkmUtils().getAllBookmarks(it.context)
                mBookmarkedDuaEvdList = DuaDb().getBookmarkedDuaEvd(bookmarksList)
                notifyItemRemoved(position)
                /*if(mBookmarksList?.contains(position+1) == true) {
                    BkmUtils().removeBookmark(position+1, it.context)
                    Toast.makeText(it.context, it.context.getString(R.string.bookmark_removed),
                        Toast.LENGTH_SHORT).show()
                    mBookmarksList = BkmUtils().getAllBookmarks(it.context)
                } else {
                    BkmUtils().saveBookmark(position + 1, it.context)
                    Toast.makeText(it.context, it.context.getString(R.string.bookmark_added), Toast.LENGTH_SHORT).show()
                    mBookmarksList = BkmUtils().getAllBookmarks(it.context)
                }*/
            }

            view.findViewById<View>(R.id.tv_btmsht_copy).setOnClickListener {
                bt.dismiss()

                MyUtils().copyToClipboard(oneDuaEvd.mDua + "\n" + oneDuaEvd.mEvidence, it.context)
                Toast.makeText(it.context, it.context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
            }
            bt.setContentView(view)
            bt.show()
        }
    }

    override fun getItemCount(): Int {
        return mBookmarkedDuaEvdList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDua: TextView = itemView.findViewById(R.id.tv_list_dua)
        val textViewEvd: TextView = itemView.findViewById(R.id.tv_list_evd)
        val viewDividerDuaEvd: View = itemView.findViewById(R.id.divider_dua_evd)
        val tvDuaNumber: TextView = itemView.findViewById(R.id.tv_dua_no)
        val imgBtnOptions: ImageButton = itemView.findViewById(R.id.btn_options)
    }
}