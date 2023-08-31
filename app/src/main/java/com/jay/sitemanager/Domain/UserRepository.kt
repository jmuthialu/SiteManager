package com.jay.sitemanager.Domain

import android.app.appsearch.SearchResults
import android.util.Log
import com.jay.sitemanager.DataModels.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiInterface: UserApiInterface
) {
    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            var users = emptyList<User>()
            try {
                users = userApiInterface.getUsers()
            } catch (e: Exception) {
                Log.d("$$$", "Exception: ${e.message}")
            }
            return@withContext users
        }
    }
}