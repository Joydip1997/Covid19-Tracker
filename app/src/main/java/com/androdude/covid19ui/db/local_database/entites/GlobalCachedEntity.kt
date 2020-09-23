package com.androdude.covid19ui.db.local_database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GLOBAL_LOCAL_DATABASE")
data class GlobalCachedEntity(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val cases: Int,
        val active: Int,
        val recovered: Int,
        val critical: Int,
        val deaths: Int

)

