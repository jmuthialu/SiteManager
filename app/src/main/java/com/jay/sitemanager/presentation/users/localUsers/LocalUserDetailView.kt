package com.jay.sitemanager.presentation.users.localUsers

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser

@Composable
fun LocalUserDetailView(user: LocalUser?) {

    Text(text = user?.name ?: "No Name")
}