package com.jay.sitemanager.presentation.users.remoteUsers

import androidx.lifecycle.ViewModel
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoteUserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    fun getUser(id: Int?) = userRepository.getRemoteUser(id)

}