package com.jay.sitemanager.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconRenderer(image: ImageVector, modifier: Modifier) {
    Image(
        imageVector = image,
        contentDescription = "Icon",
        modifier = modifier
    )
}