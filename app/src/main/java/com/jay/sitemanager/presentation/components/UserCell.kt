package com.jay.sitemanager.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun UserCell(name: String?,
             userName: String?,
             email: String?,
             phone: String?,
             modifier: Modifier
) {
    Column(modifier = modifier) {
        name.let { Text(text = it ?: "", fontWeight = FontWeight.Bold) }
        userName.let { Text(text = it ?: "", style = MaterialTheme.typography.bodySmall) }
        email.let { Text(text = it ?: "", style = MaterialTheme.typography.bodySmall) }
        phone.let { Text(text = it ?: "", style = MaterialTheme.typography.bodySmall) }
    }
}