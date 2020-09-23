package com.androdude.covid19ui.UI.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.androdude.covid19ui.R
import com.androdude.covid19ui.UI.IndiaFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import java.util.*

class DialogBox constructor(Context: Activity) {


    private lateinit var mAlertDialog: androidx.appcompat.app.AlertDialog
    private lateinit var mBuilder: MaterialAlertDialogBuilder
    private var context = Context
    fun buildDialog() {
        mBuilder = MaterialAlertDialogBuilder(context)
        mAlertDialog = mBuilder.create()
        mAlertDialog.setCancelable(false)
        val VIEW = context.layoutInflater.inflate(R.layout.dialog_box, null, false)
        mAlertDialog.setView(VIEW)

    }

    fun show() {
        mAlertDialog.show()
    }

    fun stop() {
        mAlertDialog.dismiss()
    }


}

class NoInternetDialog constructor(Context: Activity) {
    private lateinit var mAlertDialog: androidx.appcompat.app.AlertDialog
    private lateinit var mBuilder: MaterialAlertDialogBuilder
    private var context = Context


    fun buildDialog() {

        mBuilder = MaterialAlertDialogBuilder(context)
        mBuilder.setTitle("No Internet Connection")
        mBuilder.setMessage("Close the app and open again")
        mBuilder.setNegativeButton("Close", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                context.finish()
            }

        })


        mAlertDialog = mBuilder.create()
        mAlertDialog.setCancelable(false)


    }

    fun show() {
        mAlertDialog.show()
    }

    fun stop() {
        mAlertDialog.dismiss()
    }
}

