package com.itsi.almuntaqimorevn.primarylist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.itsi.almuntaqimorevn.model.DuaEvidence
import com.itsi.almuntaqimorevn.utils.BkmUtils
import com.itsi.almuntaqimorevn.utils.MyUtils
import java.text.NumberFormat
import java.util.Locale


class DuaEvdAdapter(private val mDuaEvdList: ArrayList<DuaEvidence>, private var mBookmarksList: ArrayList<Int>?) : RecyclerView.Adapter<DuaEvdAdapter.ViewHolder>() {


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
            val dua = parseAndMakeNabiSpeechRed(oneDuaEvd.mDua)
            /*val duaStr = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(i) +" - "+ oneDuaEvd.mDua*/
            /*val duaStr = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(i) +" - "+ dua*/
            //parseAndMakeNabiSpeechRed(oneDuaEvd.mDua);
            holder.tvDuaNumber.text = NumberFormat.getInstance(Locale.forLanguageTag("ar"))
                .format(i)

            if (oneDuaEvd.mQuranic) {
                val typeface = ResourcesCompat.getFont(holder.textViewDua.context, com.itsi.almuntaqimorevn.R.font.uthmanic_hafs1_ver18)
                holder.textViewDua.setTypeface(typeface)
            } else {
                val typeface = ResourcesCompat.getFont(holder.textViewDua.context, com.itsi.almuntaqimorevn.R.font.amirifont)
                holder.textViewDua.setTypeface(typeface)
            }

            holder.textViewDua.text = HtmlCompat.fromHtml(dua, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        if(!oneDuaEvd.mEvidence.equals("")) {
            holder.textViewEvd.visibility = View.VISIBLE
            holder.viewDividerDuaEvd.visibility = View.VISIBLE

            val evidence = parseAndMakeNabiSpeechRed(oneDuaEvd.mEvidence)
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
            Toast.makeText(it.context, "Clicked", Toast.LENGTH_SHORT).show()

            // Show bottom dialog
            val bt = BottomSheetDialog(it.context, R.style.BottomSheetDialogTheme)
            val view: View = LayoutInflater.from(it.context).inflate(R.layout.bottom_sheet_lay, null)

            if(mBookmarksList?.contains(position+1) == true) {
                view.findViewById<TextView>(R.id.tv_btmsht_bookmark).text = "Remove Bookmark"
            } else {
                view.findViewById<TextView>(R.id.tv_btmsht_bookmark).text = it.context.getString(R.string.bookmark)
            }

            view.findViewById<View>(R.id.tv_btmsht_share).setOnClickListener {
                //Toast.makeText(it.context, "add to cart", Toast.LENGTH_LONG).show()
                bt.dismiss()

                /*Create an ACTION_SEND Intent*/
                val intent = Intent(Intent.ACTION_SEND)
                /*This will be the actual content you wish you share.*/
                val shareBody = oneDuaEvd.mDua + "\n" + oneDuaEvd.mEvidence
                /*The type of the content is text, obviously.*/
                intent.type = "text/plain"
                /*Applying information Subject and Body.*/
                intent.putExtra(Intent.EXTRA_SUBJECT, it.context.getString(R.string.app_name))
                intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                /*Fire!*/
                it.context.startActivity(Intent.createChooser(intent,
                    it.context.getString(R.string.share_using)))
            }

            view.findViewById<View>(R.id.tv_btmsht_bookmark).setOnClickListener {
                bt.dismiss()

                if(mBookmarksList?.contains(position+1) == true) {
                    BkmUtils().removeBookmark(position+1, it.context)
                    Toast.makeText(it.context, "Bookmark removed", Toast.LENGTH_SHORT).show()
                    mBookmarksList = BkmUtils().getAllBookmarks(it.context)
                } else {
                    BkmUtils().saveBookmark(position + 1, it.context)
                    Toast.makeText(it.context, "Bookmark added", Toast.LENGTH_SHORT).show()
                    mBookmarksList = BkmUtils().getAllBookmarks(it.context)
                }
            }

            view.findViewById<View>(R.id.tv_btmsht_copy).setOnClickListener {
                bt.dismiss()

                val clipboard: ClipboardManager? = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText(it.context.getString(R.string.app_name), oneDuaEvd.mDua + "\n" + oneDuaEvd.mEvidence)
                clipboard?.setPrimaryClip(clip)

                Toast.makeText(it.context, "Copied", Toast.LENGTH_SHORT).show()
            }
            bt.setContentView(view)
            bt.show()
        }
    }

    /***
     * TOD: Make logic for spanning with span array to show Nabi's speech in red
     */
    private fun parseAndMakeNabiSpeechRed(duaStr: String): String {
        val startRedSymbol = "«"
        val endRedSymbol = "»"
        var duaString = duaStr

        var index: Int = duaString.indexOf(startRedSymbol)
        while (index != -1) {
            duaString = StringBuilder(duaString).insert(index+1, "<font color='#D2042D'>").toString()
            index = duaString.indexOf(startRedSymbol, index + 1)
            Log.d("MATCHER $index", duaString)
        }

        index = duaString.indexOf(endRedSymbol)
        while (index != -1) {
            duaString = StringBuilder(duaString).insert(index+1, "</font>").toString()
            index = duaString.indexOf(endRedSymbol, index + 1)
            Log.d("MATCHER $index", duaString)
        }


        /*val matcher1: Matcher = Pattern.compile(startRedSymbol).matcher(duaStr)
        while (matcher1.find()) {
            val index = matcher1.start()
            duaString = StringBuilder(duaString).insert(index, "<font color='#D2042D'>").toString()
            Log.d("MATCHER1", duaString)
        }
        val matcher2: Matcher = Pattern.compile(endRedSymbol).matcher(duaStr)
        while (matcher2.find()) {
            val index = matcher2.start()
            duaString = StringBuilder(duaString).insert(index, "</font>").toString()
            Log.d("MATCHER2", duaString)
        }*/
        Log.d("DUASTRING", duaString)
        return duaString
    }

    override fun getItemCount(): Int {
        return mDuaEvdList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDua: TextView = itemView.findViewById(R.id.tv_list_dua)
        val textViewEvd: TextView = itemView.findViewById(R.id.tv_list_evd)
        val viewDividerDuaEvd: View = itemView.findViewById(R.id.divider_dua_evd)
        val tvDuaNumber: TextView = itemView.findViewById(R.id.tv_dua_no)
        val imgBtnOptions: ImageButton = itemView.findViewById(R.id.btn_options)
    }
}