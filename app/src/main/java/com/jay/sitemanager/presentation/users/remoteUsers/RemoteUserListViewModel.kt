package com.jay.sitemanager.presentation.users.remoteUsers

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RemoteUserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val _usersState = mutableStateOf(emptyList<RemoteUser>())
    val usersState = _usersState

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        Log.d("SM", "errorHandler: ${exception.message}"   )
    }

    fun getRemoteUsers() {
        viewModelScope.launch(errorHandler) {
            _usersState.value = userRepository.getRemoteUsers()
        }
    }

}