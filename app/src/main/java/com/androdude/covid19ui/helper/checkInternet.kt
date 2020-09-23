package com.androdude.covid19ui.helper

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.Exception


class checkInternet(var context: Context) : Interceptor  {



    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkConnected())
        {
            throw IOException("No Internet")
        }
        return chain.proceed(chain.request())
    }


}