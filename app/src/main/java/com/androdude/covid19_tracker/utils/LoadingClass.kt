package com.androdude.covid19_tracker.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.LinearGradient
import android.text.Layout
import android.view.View
import android.widget.LinearLayout
import com.androdude.covid19_tracker.R
import kotlinx.android.synthetic.main.activity_main.*

class LoadingClass() {
    private var  progresDialog: ProgressDialog? = null
    private var context:Context? =null
    private var layout:LinearLayout? = null
    
    fun get(Context: Context, Layout:LinearLayout)
    {
        context=Context
        layout=Layout
    }
    fun run()
    {
        progresDialog= ProgressDialog(context)
        progresDialog!!.show()
        progresDialog!!.setContentView(R.layout.loading_screen)

        layout!!.visibility = View.GONE
        progresDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
    
    fun stop()
    {
        progresDialog!!.hide()
        layout!!.visibility=View.VISIBLE
    }
}