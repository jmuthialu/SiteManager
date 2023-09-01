package com.jay.sitemanager.navigationInfrastructure

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Bluetooth : Screen("bleList", "BT Scanner", Icons.Filled.AccountBox)
    object User : Screen("usersList", "Users", Icons.Filled.List)
    object RemoteUserDetail : Screen("usersList/remote", "RemoteUserDetail", Icons.Filled.List)
    object LocalUserDetail : Screen("usersList/local", "LocalUserDetail", Icons.Filled.List)
}