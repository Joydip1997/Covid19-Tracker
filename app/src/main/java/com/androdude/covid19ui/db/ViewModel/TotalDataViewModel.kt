package com.androdude.covid19_appupdate.db.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.androdude.covid19_appupdate.db.ModelClass.Total.TotalResponse
import com.androdude.covid19_appupdate.db.Repository
import com.androdude.covid19ui.db.local_database.entites.GlobalCachedEntity
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response

class TotalDataViewModel constructor( application: Application) : AndroidViewModel(application) {

    private var repository : Repository = Repository(application)

    fun getTotalDetails() : LiveData<Response<TotalResponse>> = liveData {
        val res =  repository!!.getTotalDetails()
        emit(res)
    }

    // TODO: 23-09-2020 Caching implementations
    fun upsertGlobalData(globalCachedEntity: GlobalCachedEntity) = viewModelScope.launch(Dispatchers.IO) {
        val cache =repository!!.upsertGlobal(globalCachedEntity)
    }

    // TODO: 23-09-2020 Caching implementations
    fun getGlobalCahcedData()  = repository!!.getGlobalCachedData()
}