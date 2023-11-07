package com.itsi.almuntaqimorevn

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val contactEmailTv = binding.tvContactEmail
        val introductionTv = binding.tvIntroduction
        val authorDescTv = binding.tvAuthorDesc

        shareTv.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context?.getString(R.string.app_name))
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage = "$shareMessage https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        contactEmailTv.setOnClickListener {

            // SHow dialog
            val view: View = layoutInflater.inflate(R.layout.dialog_contact_email, null)
            val alertDialog: AlertDialog = AlertDialog.Builder(it.context).create()
            alertDialog.setTitle("اتصل بنا")
            //alertDialog.setIcon("Icon id here")
            alertDialog.setCancelable(false)

            val etFeedback = view.findViewById<View>(R.id.et_feedback) as TextInputEditText
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "تمام", DialogInterface.OnClickListener{ dialog, which ->

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
                AlertDialog.BUTTON_NEGATIVE, "إلغاء", DialogInterface.OnClickListener{ dialog, which ->
                    alertDialog.dismiss()
                    Toast.makeText(it.context,"clicked no",Toast.LENGTH_LONG).show()
                }
            )
            alertDialog.setView(view);
            alertDialog.show();
        }

        introductionTv.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.dialog_introduction_title))
                .setMessage(resources.getString(R.string.dialog_introduction_body))

                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()
        }

        authorDescTv.setOnClickListener{
            var authorDesc = "<b>" + resources.getString(R.string.author_name) + "</b> "+
                    "\n" + resources.getString(R.string.dialog_author_desc)
            val dialog = MaterialAlertDialogBuilder(it.context, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.author))
                .setMessage(HtmlCompat.fromHtml(authorDesc, HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()
        }

        return binding.root
    }
}