package com.jay.sitemanager.presentation.users.localUsers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser

@Composable
fun LocalUserDetailView(title: MutableState<String>, user: LocalUser?, modifier: Modifier) {

    title.value = user?.name?.let { it } ?: run { "" }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        user?.email?.let {
            Text(text = "Email: $it")
        }

        user?.phone?.let {
            Text(text = "Phone: $it")
        }

    }
}