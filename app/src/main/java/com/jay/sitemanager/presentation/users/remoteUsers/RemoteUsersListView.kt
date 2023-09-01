package com.jay.sitemanager.presentation.users.remoteUsers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.dataModels.RemoteUser
import com.jay.sitemanager.presentation.components.IconRenderer
import com.jay.sitemanager.presentation.components.UserCell

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteUsersListView(usersState: List<RemoteUser>,
                    bottomModifier: Modifier,
                    onClick: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = bottomModifier,
        contentPadding = PaddingValues(
            vertical = 10.dp,
            horizontal = 10.dp
        )
    ) {
        items(usersState) { user ->
            UserItem(user, onClick)
        }
    }
}

@Composable
fun UserItem(item: RemoteUser, onclick: (Int) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                item.id?.let { onclick(it) }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            IconRenderer(image = Icons.Filled.Person, modifier = Modifier.weight(0.15f))
            UserCell(name = item.name,
                    userName = item.username,
                    email = item.email,
                    phone = item.phone,
                    modifier = Modifier.weight(0.85f)
            )
        }

    }
}
