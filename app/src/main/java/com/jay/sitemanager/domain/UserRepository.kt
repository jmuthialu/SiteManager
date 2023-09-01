package com.jay.sitemanager.domain

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.AppConstants
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.persistence.RemoteUsersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiInterface: UserApiInterface,
    private val remoteUsersDao: RemoteUsersDao
) {

    var remoteUsers = emptyList<RemoteUser>()
    var localUsers = emptyList<LocalUser>()

    suspend fun getRemoteUsers(): List<RemoteUser> {
        return withContext(Dispatchers.IO) {
            remoteUsers = emptyList<RemoteUser>()
            try {
                remoteUsers = userApiInterface.getRemoteUsers()
                remoteUsersDao.addAll(remoteUsers)
            } catch (e: Exception) {
                Log.d(AppConstants.TAG, "Exception: ${e.message}")
            }
            remoteUsers = remoteUsersDao.getAll()
            return@withContext remoteUsers
        }
    }

    fun getRemoteUser(id: Int?): RemoteUser? {
        val user = remoteUsers.find { it.id == id }
        Log.d(AppConstants.TAG, "getRemoteUser: $user")
        return user
    }

    fun getLocalUsers(context: Context): List<LocalUser> {
        var usersJsonString = ""
        localUsers = emptyList()

        try {
            usersJsonString = context.assets.open(AppConstants.LOCAL_USERS_FILE_NAME)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("SM", "Error - getLocalUsers: ${ioException.message}")
        }

        val usersType = object : TypeToken<List<LocalUser>>() {}.type
        localUsers = Gson().fromJson(usersJsonString, usersType)
        return  localUsers
    }

    fun getLocalUser(id: Int?): LocalUser? {
        val user = localUsers.find { it.id == id }
        Log.d(AppConstants.TAG, "getLocalUser: $user")
        return user
    }
}