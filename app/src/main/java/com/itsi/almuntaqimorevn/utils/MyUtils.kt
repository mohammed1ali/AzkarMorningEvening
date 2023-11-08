package com.itsi.almuntaqimorevn.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import com.itsi.almuntaqimorevn.R

class MyUtils {
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, just pass null.
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, just pass null.
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    /***
     * TOD: Make logic for spanning with span array to show Nabi's speech in red
     * TextType 1 for Dua 2 for Evidence
     */
    fun parseAndMakeNabiSpeechRed(context: Context, duaStr: String, textType:Int): String {
        val startRedSymbol = "«"
        val endRedSymbol = "»"
        var duaString = duaStr
        var color:Int
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if(textType == TEXT_TYPE_DUA)
                color = context.resources.getColor(R.color.txt_nabi_speech)
            else
                color = context.resources.getColor(R.color.txt_nabi_speech_evd)
        } else {
            if(textType == TEXT_TYPE_DUA)
                color = context.resources.getColor(R.color.txt_nabi_speech, null)
            else
                color = context.resources.getColor(R.color.txt_nabi_speech_evd, null)
        }

        var index: Int = duaString.indexOf(startRedSymbol)
        while (index != -1) {
            duaString = StringBuilder(duaString)
                .insert(index+1, "<font color='" + color.toString() + "'>").toString()
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

    fun actionShare(shareBody: String, context: Context?) {
        /*Create an ACTION_SEND Intent*/
        val intent = Intent(Intent.ACTION_SEND)
        /*The type of the content is text, obviously.*/
        intent.type = "text/plain"
        /*Applying information Subject and Body.*/
        intent.putExtra(Intent.EXTRA_SUBJECT, context?.getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        /*Fire!*/
        context?.startActivity(Intent.createChooser(intent,
            context?.getString(R.string.share_using)
        ))
    }

    fun copyToClipboard(copyStr: String, context: Context?) {
        val clipboard: ClipboardManager? = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText(context?.getString(R.string.app_name), copyStr)
        clipboard?.setPrimaryClip(clip)
    }

    fun getFontSize(context: Context) : Int {
        val sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val a = sh.getInt("font_size", 24)
        return a
    }

    fun saveFontSize(context: Context, fontSize: Int) {
        val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        myEdit.putInt("font_size", fontSize)
        myEdit.apply()
    }

    companion object {
        const val TEXT_TYPE_DUA = 1
        const val TEXT_TYPE_EVIDENCE = 2
    }


}