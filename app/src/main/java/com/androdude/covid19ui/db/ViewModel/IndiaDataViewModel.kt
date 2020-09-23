package com.androdude.covid19_appupdate.db.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.androdude.covid19_appupdate.db.Repository
import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class IndiaDataViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)

    fun getIndiaDetails() :  LiveData<Response<IndiaResponse>> = liveData {
      val response = repository!!.getIndiaDetails()
        emit(response)
    }

    // TODO: 23-09-2020 Caching implementations
    fun upsertIndiaData(indiaDatabase: IndiaCachedEntity) = viewModelScope.launch(Dispatchers.IO)
    {
        repository!!.upsertIndia(indiaDatabase)
    }

    fun getIndiaCachedData() : LiveData<IndiaCachedEntity>  = repository!!.getIndiaCachedData()


}