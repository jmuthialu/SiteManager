package com.jay.sitemanager.presentation.users.localUsers

import androidx.lifecycle.ViewModel
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalUserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    fun getUser(id: Int?) = userRepository.getLocalUser(id)

}