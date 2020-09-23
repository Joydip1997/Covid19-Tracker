package com.androdude.covid19ui.db.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androdude.covid19ui.db.local_database.entites.GlobalCachedEntity
import com.androdude.covid19ui.db.local_database.entites.IndiaCachedEntity

@Database(entities = arrayOf(IndiaCachedEntity::class,GlobalCachedEntity::class),version = 1,exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract val dao : Dao

    companion object
    {
        @Volatile
        private  var INSTANCE : LocalDatabase ?= null

        fun getInstance(context: Context) : LocalDatabase
        {
            if(INSTANCE==null)
            {
                synchronized(this)
                {

                    if(INSTANCE== null)
                    {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                LocalDatabase ::class.java,
                                "USER_DB").build()
                    }

                }

            }
            return INSTANCE!!

        }

    }
}