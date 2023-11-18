package com.itsi.almuntaqimorevn

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.itsi.almuntaqimorevn.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val shareTv = binding.tvShareApp
        val sourceTv = binding.tvSource
        val contactEmailTv = binding.tvContactEmail
        val introductionTv = binding.tvIntroduction
        val authorDescTv = binding.tvAuthorDesc
        val lightNightSwitch = binding.switchNightMode

        shareTv.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context?.getString(R.string.app_name))
                var shareMessage = getString(R.string.share_app) + "\n\n"
                shareMessage = "$shareMessage https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        sourceTv.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.dialog_app_source, null)
            val sourceDialogMsgTv = view.findViewById<TextView>(R.id.tv_source_dialog_message)
            sourceDialogMsgTv.movementMethod = LinkMovementMethod.getInstance()
            sourceDialogMsgTv.text = HtmlCompat.fromHtml(
                getString(R.string.source_text), HtmlCompat.FROM_HTML_MODE_LEGACY)

            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(getString(R.string.source))
                .setView(view)
                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()
        }


        contactEmailTv.setOnClickListener {

            // SHow dialog
            val view: View = layoutInflater.inflate(R.layout.dialog_contact_email, null)
            val alertDialog: AlertDialog = AlertDialog.Builder(it.context).create()
            alertDialog.setTitle("اتصل بنا")
            //alertDialog.setIcon("Icon id here")
            //alertDialog.setCancelable(false)

            val etFeedback = view.findViewById<View>(R.id.et_feedback) as TextInputEditText
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), DialogInterface.OnClickListener{ dialog, which ->

                    val intent = Intent(Intent.ACTION_SENDTO)
                    //intent.type = "message/rfc822"      // Type("message/rfc822") so it won't show you all of the apps that support the send intent.
                    intent.data = Uri.parse("mailto:"); // only email apps
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("itsindonesia2023@gmail.com"))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of "+it.context?.getString(R.string.app_name))
                    intent.putExtra(Intent.EXTRA_TEXT, etFeedback.text)
                    try {
                        startActivity(Intent.createChooser(intent, "ارسل بريد الكتروني"))
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(it.context, "There are no email clients installed.",
                            Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(it.context,"${etFeedback.text}",Toast.LENGTH_LONG).show()
                }
            )
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), DialogInterface.OnClickListener{ dialog, which ->
                    alertDialog.dismiss()
                    //Toast.makeText(it.context,"clicked no",Toast.LENGTH_LONG).show()
                }
            )
            alertDialog.setView(view);
            alertDialog.show();
        }

        introductionTv.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.dialog_introduction, null)
            val introductionDialogMsgTv = view.findViewById<TextView>(R.id.tv_introduction_dialog_message)
            introductionDialogMsgTv.movementMethod = LinkMovementMethod.getInstance()
            introductionDialogMsgTv.text = HtmlCompat.fromHtml(
                getString(R.string.dialog_introduction_body), HtmlCompat.FROM_HTML_MODE_LEGACY)

            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(HtmlCompat.fromHtml(
                    getString(R.string.dialog_introduction_title), HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setView(view)
                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()


            /*val dialog1 = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.dialog_introduction_title))
                .setMessage(resources.getString(R.string.dialog_introduction_body))

                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()*/
        }

        authorDescTv.setOnClickListener{
            val view: View = layoutInflater.inflate(R.layout.dialog_author_desc, null)
            val authorDescDialogMsgTv = view.findViewById<TextView>(R.id.tv_author_desc_dialog_message)
            val msgBelowDescDialogMsgTv = view.findViewById<TextView>(R.id.tv_msg_below_author_desc)

            authorDescDialogMsgTv.movementMethod = LinkMovementMethod.getInstance()
            authorDescDialogMsgTv.text = HtmlCompat.fromHtml(
                getString(R.string.author_name), HtmlCompat.FROM_HTML_MODE_LEGACY)

            msgBelowDescDialogMsgTv.movementMethod = LinkMovementMethod.getInstance()
            msgBelowDescDialogMsgTv.text = HtmlCompat.fromHtml(
                getString(R.string.telegram_channel), HtmlCompat.FROM_HTML_MODE_LEGACY)

            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(HtmlCompat.fromHtml(
                    getString(R.string.author), HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setView(view)
                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()




            /*var authorDesc = "<b>" + resources.getString(R.string.author_name) + "</b> "+
                    "<br/>" + resources.getString(R.string.dialog_author_desc)
            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.author))
                .setMessage(HtmlCompat.fromHtml(authorDesc, HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()
            dialog.findViewById<TextView>(android.R.id.message)?.movementMethod = LinkMovementMethod.getInstance()*/
        }
        //authorDescTv.movementMethod = LinkMovementMethod.getInstance()


        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> { lightNightSwitch.isChecked = true}
            Configuration.UI_MODE_NIGHT_NO -> { lightNightSwitch.isChecked = false}
            else -> { lightNightSwitch.isChecked = false}
        }

        lightNightSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                changeToDarkMode()
            } else {
                changeToLightMode()
            }
        }

        return binding.root
    }

    private fun changeToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun changeToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}