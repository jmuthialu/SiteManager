package com.jay.sitemanager.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.dataModels.RemoteUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListView(usersState: List<RemoteUser>) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 10.dp,
            horizontal = 10.dp
        )
    ) {
        items(usersState) { user ->
            UserItem(user)
        }
    }
}

@Composable
fun UserItem(item: RemoteUser) {
    Card(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            UserIcon(image = Icons.Filled.Person, modifier = Modifier.weight(0.15f))
            UserCell(name = item.name,
                userName = item.username,
                modifier = Modifier.weight(0.85f))
        }

    }
}

@Composable
fun UserIcon(image: ImageVector, modifier: Modifier) {
    Image(
        imageVector = image,
        contentDescription = "Restaurant icon",
        modifier = modifier
    )
}

@Composable
fun UserCell(name: String?, userName: String?, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = (name ?: "no name" ), style = MaterialTheme.typography.bodyMedium)
        Text(text = (userName ?: "no userName"), style = MaterialTheme.typography.bodySmall)
    }
}