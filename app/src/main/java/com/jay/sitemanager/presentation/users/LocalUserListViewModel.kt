package com.jay.sitemanager.presentation.users

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocalUserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val _usersState = mutableStateOf(emptyList<RemoteUser>())
    val usersState = _usersState
    fun getLocalUsers(context: Context) {

        var usersJsonString = ""
        try {
            usersJsonString = context.assets.open("users.json")
                    .bufferedReader()
                    .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("SM", "Error - getLocalUsers: ${ioException.message}")
        }
        val usersType = object : TypeToken<List<RemoteUser>>() {}.type
        _usersState.value = Gson().fromJson(usersJsonString, usersType)
    }

}