package com.jay.sitemanager.presentation.users.remoteUsers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.dataModels.RemoteUser

@Composable
fun RemoteUserDetailView(user: RemoteUser?) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        user?.name?.let {
            Text(text = "Name: $it")
        }

        user?.address?.street.let {
            Text(text = "Street: $it")
        }

        user?.address?.geo?.lat.let {
            Text(text = "Lat: $it")
        }

        user?.address?.geo?.lng.let {
            Text(text = "Lon: $it")
        }

        user?.company?.name?.let {
            Text(text = "Company Name: $it")
        }


    }



}