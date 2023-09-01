package com.jay.sitemanager.presentation.users.localUsers

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocalUserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val _usersState = mutableStateOf(emptyList<LocalUser>())
    val usersState = _usersState
    fun getLocalUsers(context: Context) {
        val localUsers = userRepository.getLocalUsers(context)
        _usersState.value = localUsers
    }


}