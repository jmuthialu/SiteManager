package com.jay.sitemanager.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.sitemanager.DataModels.RemoteUser
import com.jay.sitemanager.Domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val _usersState = mutableStateOf(emptyList<RemoteUser>())
    val usersState = _usersState

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        Log.d("SM", "errorHandler: ${exception.message}"   )
    }

    fun getUsers() {
        viewModelScope.launch(errorHandler) {
            _usersState.value = userRepository.getUsers()
        }
    }

}