package com.jay.sitemanager.di

import android.content.Context
import androidx.room.Room
import com.jay.sitemanager.persistence.RemoteUsersDB
import com.jay.sitemanager.persistence.RemoteUsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistanceModule {

    @Singleton
    @Provides
    fun providesRemoteUsersDB(@ApplicationContext appContext: Context): RemoteUsersDB {
        return Room.databaseBuilder(
            appContext,
            RemoteUsersDB::class.java,
            "remoteUsersDB",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesRemoteUsersDao(remoteUsersDB: RemoteUsersDB): RemoteUsersDao {
        return remoteUsersDB.dao
    }

}