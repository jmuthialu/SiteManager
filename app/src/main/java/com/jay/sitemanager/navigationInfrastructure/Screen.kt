package com.jay.sitemanager.navigationInfrastructure

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.jay.sitemanager.R

sealed class Screen(val route: String, val label: String, val icon: Int) {
    object Bluetooth : Screen("bleList", "BT Scanner", R.drawable.bluetooth)
    object User : Screen("usersList", "Users", R.drawable.user)
    object RemoteUserDetail : Screen("usersList/remote", "RemoteUserDetail", R.drawable.user)
    object LocalUserDetail : Screen("usersList/local", "LocalUserDetail", R.drawable.user)
}