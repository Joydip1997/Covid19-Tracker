package com.androdude.covid19_appupdate.db.api

import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19ui.helper.checkInternet
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface apiInterFaceIndia {

    @GET("data.json")
    suspend fun getIndiaDetails() : Response<IndiaResponse>

    companion object
    {

       operator fun invoke() : apiInterFaceIndia
        {


            return Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create()).build().create(apiInterFaceIndia::class.java)
        }
    }
}