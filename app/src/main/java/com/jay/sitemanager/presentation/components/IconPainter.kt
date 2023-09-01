package com.jay.sitemanager.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.R

@Composable
fun IconPainter(id: Int, size: Int, tintColor: Color? = null) {
    if (tintColor != null) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier.size(size.dp)
        )
    } else {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(size.dp)
        )
    }
}