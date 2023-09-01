package com.jay.sitemanager.domain

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiInterface: UserApiInterface
) {

    var remoteUsers = emptyList<RemoteUser>()
    var localUsers = emptyList<LocalUser>()
    suspend fun getRemoteUsers(): List<RemoteUser> {
        return withContext(Dispatchers.IO) {
            remoteUsers = emptyList<RemoteUser>()
            try {
                remoteUsers = userApiInterface.getRemoteUsers()
            } catch (e: Exception) {
                Log.d("$$$", "Exception: ${e.message}")
            }
            return@withContext remoteUsers
        }
    }

    fun getRemoteUser(id: Int?): RemoteUser? {
        val user = remoteUsers.find { it.id == id }
        Log.d("$$$", "getUser: $user")
        return user
    }

    fun getLocalUsers(context: Context): List<LocalUser> {
        var usersJsonString = ""
        localUsers = emptyList<LocalUser>()

        try {
            usersJsonString = context.assets.open("users.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("SM", "Error - getLocalUsers: ${ioException.message}")
        }

        val usersType = object : TypeToken<List<LocalUser>>() {}.type
        localUsers = Gson().fromJson(usersJsonString, usersType)
        return  localUsers
    }
}