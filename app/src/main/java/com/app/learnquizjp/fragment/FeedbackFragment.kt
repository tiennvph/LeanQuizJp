package com.app.learnquizjp.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment

class FeedbackFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Thanks for your feedback!")
                .setMessage("Do you have anything you wish to say to the developer our app ?")
                .setPositiveButton("SEND EMAIL") { _, _ ->
                    run {
                        val emailIntent = Intent(Intent.ACTION_SENDTO)
                        emailIntent.type = "text/plain"
                        emailIntent.data = Uri.parse("mailto:")
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("namntph06792@fpt.edu.vn"))
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "LearnQuizJP Feedback - ${android.os.Build.BRAND + " " + android.os.Build.MODEL}")

                        startActivity(Intent.createChooser(emailIntent, "Send e-mail using..."))
                    }
                }
                .setNegativeButton("CANCEL") { _, _ ->
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}