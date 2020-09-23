package com.androdude.covid19_appupdate.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.androdude.covid19_appupdate.db.api.apiInterFaceIndia
import com.androdude.covid19_appupdate.db.api.apiInterfaceWorldWide
import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19_appupdate.db.ModelClass.Total.TotalResponse
import com.androdude.covid19_appupdate.db.ModelClass.WordWide.WorldWideResponse
import com.androdude.covid19_appupdate.db.api.apiInterFaceTotal
import com.androdude.covid19ui.db.local_database.Dao
import com.androdude.covid19ui.db.local_database.LocalDatabase
import com.androdude.covid19ui.db.local_database.entites.GlobalCachedEntity
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity
import retrofit2.Response


class Repository constructor(context: Context) {

    private var mDao : Dao? =null
    private var mIndiaCachedData : LiveData<IndiaCachedEntity>? = null
    private var mGlobalCachedData : LiveData<GlobalCachedEntity>? = null

    init {
        val db = LocalDatabase.getInstance(context)
        mDao = db.dao
        mIndiaCachedData = mDao!!.getIndiaCachedData()
        mGlobalCachedData = mDao!!.getGlobalCachedData()
    }

    // TODO: 23-09-2020 Network calls
    suspend fun getIndiaDetails() : Response<IndiaResponse>
    {
       return apiInterFaceIndia().getIndiaDetails()
    }

    suspend fun getWorldWideDetails() : Response<WorldWideResponse>
    {
       return apiInterfaceWorldWide().getWorldDetails()
    }

    suspend fun getTotalDetails() : Response<TotalResponse>
    {
        return apiInterFaceTotal().getTotalCases()
    }


    // TODO: 23-09-2020 Use it for caching
    suspend fun upsertIndia(indiaDatabase: IndiaCachedEntity)
    {
        mDao!!.UpsertIndia(indiaDatabase)
    }

    fun getIndiaCachedData() : LiveData<IndiaCachedEntity>
    {
        return mIndiaCachedData!!
    }

    suspend fun upsertGlobal(globalCachedEntity: GlobalCachedEntity)
    {
        mDao!!.UpsertGlobal(globalCachedEntity)
    }

    fun getGlobalCachedData() : LiveData<GlobalCachedEntity>
    {
        return mGlobalCachedData!!
    }





}