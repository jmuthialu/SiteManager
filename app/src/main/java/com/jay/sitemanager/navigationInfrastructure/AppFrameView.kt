package com.jay.sitemanager.navigationInfrastructure

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jay.sitemanager.ble.BLEFacade
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class,
    InternalCoroutinesApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun AppFrameView(context: Context, bleFacade: BLEFacade) {

    val navController = rememberNavController()
    val bottomNavItems = listOf(
        Screen.Bluetooth,
        Screen.User,
    )
    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(navController, bottomNavItems)
        },
    ) { bottomPadding ->
        NavGraph(navController = navController,
            context = context,
            bleFacade = bleFacade,
            bottomModifier = Modifier.padding(bottomPadding)
        )
    }
}