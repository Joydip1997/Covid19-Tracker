package com.androdude.covid19_appupdate.db.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.androdude.covid19_appupdate.db.Repository
import com.androdude.covid19_appupdate.db.ModelClass.WordWide.WorldWideResponse
import retrofit2.Response

class WorldWideDataViewModel : ViewModel() {

    fun getWorldDetails(application: Application) : LiveData<Response<WorldWideResponse>> = liveData {
        val response = Repository(application).getWorldWideDetails()
        emit(response)
    }
}