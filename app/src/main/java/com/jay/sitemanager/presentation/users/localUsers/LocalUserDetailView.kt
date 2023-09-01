package com.jay.sitemanager.presentation.users.localUsers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.R
import com.jay.sitemanager.dataModels.LocalUser
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.presentation.components.ProfileImageView

@Composable
fun LocalUserDetailView(title: MutableState<String>, user: LocalUser?, modifier: Modifier) {

    title.value = user?.name?.let { it } ?: run { "" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        ProfileImageView(imageId = R.drawable.user_profile, imageSize = 300)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Email:", fontWeight = FontWeight.Bold)
        user?.email?.let {
            Text(text = "$it")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Phone:", fontWeight = FontWeight.Bold)
        user?.phone?.let {
            Text(text = "$it")
        }
    }
}