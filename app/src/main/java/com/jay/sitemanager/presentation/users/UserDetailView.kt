package com.jay.sitemanager.presentation.users

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jay.sitemanager.dataModels.RemoteUser

@Composable
fun UserDetailView(user: RemoteUser?) {

    Text(text = user?.name ?: "No Name")
}