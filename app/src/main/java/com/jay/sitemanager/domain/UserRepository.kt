package com.jay.sitemanager.domain

import android.util.Log
import com.jay.sitemanager.dataModels.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiInterface: UserApiInterface
) {
    suspend fun getUsers(): List<RemoteUser> {
        return withContext(Dispatchers.IO) {
            var users = emptyList<RemoteUser>()
            try {
                users = userApiInterface.getRemoteUsers()
            } catch (e: Exception) {
                Log.d("$$$", "Exception: ${e.message}")
            }
            return@withContext users
        }
    }
}