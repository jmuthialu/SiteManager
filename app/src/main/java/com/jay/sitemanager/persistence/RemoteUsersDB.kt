package com.jay.sitemanager.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jay.sitemanager.dataModels.RemoteUser

@Database(
    entities = [RemoteUser::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class RemoteUsersDB : RoomDatabase() {
    abstract val dao: RemoteUsersDao
}