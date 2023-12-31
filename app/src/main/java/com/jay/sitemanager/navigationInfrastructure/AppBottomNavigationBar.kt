package com.jay.sitemanager.navigationInfrastructure

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jay.sitemanager.AppConstants
import com.jay.sitemanager.R
import com.jay.sitemanager.presentation.components.IconPainter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@FlowPreview
@ExperimentalAnimationApi
@InternalCoroutinesApi
@Composable
fun AppBottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>
) {
    BottomNavigation {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Log.d(AppConstants.TAG, "currentRoute: $currentRoute")

        items.forEach { item ->
            BottomNavigationItem(
                icon = { IconPainter(id = item.icon, size = 24, tintColor = Color.White,) },
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start to avoid build up of destinations
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}