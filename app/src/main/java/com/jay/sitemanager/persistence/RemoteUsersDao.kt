package com.jay.sitemanager.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jay.sitemanager.dataModels.RemoteUser

@Dao
interface RemoteUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(photos: List<RemoteUser>)

    @Query("SELECT * FROM RemoteUser")
    suspend fun getAll(): List<RemoteUser>

    @Query("DELETE FROM RemoteUser")
    suspend fun deleteAll()

}