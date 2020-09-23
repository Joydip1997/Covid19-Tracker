package com.androdude.covid19ui.db.local_database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INDIA_LOCAL_DATABASE")
data class IndiaCachedEntity (
        @PrimaryKey(autoGenerate = false)
        val id : Int,
        val active: String,
        val confirmed: String,
        val deaths: String,
        val recovered: String,
        val lastupdatedtime: String

)