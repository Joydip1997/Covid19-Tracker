package com.androdude.covid19_appupdate.db.api

import com.androdude.covid19_appupdate.db.ModelClass.WordWide.WorldWideResponse
import com.androdude.covid19ui.helper.checkInternet
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface apiInterfaceWorldWide {

    @GET("countries")
    suspend fun getWorldDetails() : Response<WorldWideResponse>


    companion object
    {

        operator fun invoke() : apiInterfaceWorldWide
        {
            return Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).
            baseUrl("https://corona.lmao.ninja/v2/")
                .build().create(apiInterfaceWorldWide::class.java)
        }
    }

}