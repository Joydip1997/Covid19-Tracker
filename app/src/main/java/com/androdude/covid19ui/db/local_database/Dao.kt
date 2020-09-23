package com.androdude.covid19ui.db.local_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androdude.covid19ui.db.local_database.entites.GlobalCachedEntity
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun UpsertIndia(indiaCachedEntity : IndiaCachedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun UpsertGlobal(globalCachedEntity: GlobalCachedEntity)

    @Query("SELECT * FROM INDIA_LOCAL_DATABASE WHERE id == 100")
    fun getIndiaCachedData() : LiveData<IndiaCachedEntity>

    @Query("SELECT * FROM GLOBAL_LOCAL_DATABASE WHERE id == 101")
    fun getGlobalCachedData() :  LiveData<GlobalCachedEntity>
}