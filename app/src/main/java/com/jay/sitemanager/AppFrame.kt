package com.jay.sitemanager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.presentation.ble.BLEListView
import com.jay.sitemanager.presentation.ble.BLEListViewModel
import com.jay.sitemanager.presentation.users.LocalUserListViewModel
import com.jay.sitemanager.presentation.users.LocalUsersListView
import com.jay.sitemanager.presentation.users.RemoteUserDetailView
import com.jay.sitemanager.presentation.users.RemoteUserListViewModel
import com.jay.sitemanager.presentation.users.RemoteUsersListView
import com.jay.sitemanager.presentation.users.RemoteUserDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Bluetooth : Screen("bleList", "BT Scanner", Icons.Filled.AccountBox)
    object User : Screen("usersList", "Users", Icons.Filled.List)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class,
    InternalCoroutinesApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun AppFrame(context: Context, bleFacade: BLEFacade) {

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

@Composable
fun NavGraph(navController: NavHostController,
             context: Context,
             bleFacade: BLEFacade,
             bottomModifier : Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Bluetooth.route) {

        composable(route = Screen.Bluetooth.route) {
            val bleViewModel: BLEListViewModel = hiltViewModel()
            bleViewModel.bleFacade = bleFacade
            BLEListView(viewModel = bleViewModel, bottomModifier = bottomModifier)
        }

        composable(route = Screen.User.route) {
            val localUserViewModel: LocalUserListViewModel = hiltViewModel()
            localUserViewModel.getLocalUsers(context = context)
            val remoteUserListViewModel: RemoteUserListViewModel = hiltViewModel()
            remoteUserListViewModel.getRemoteUsers()
            TabView(
                titles = listOf("Local Users", "Remote Users"),
                localUserView = { LocalUsersListView(
                    usersState = localUserViewModel.usersState.value,
                    bottomModifier = bottomModifier
                ) },
                remoteUserView = { RemoteUsersListView(
                    usersState = remoteUserListViewModel.usersState.value,
                    bottomModifier = bottomModifier
                ) { id ->
                    navController.navigate(Screen.User.route +"/$id")
                    }
                }
            )
        }

        composable(
            route = Screen.User.route +"/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })) { backStackEntry ->
            val userId = backStackEntry.arguments?.get("userId") as Int?
            val viewModel: RemoteUserDetailViewModel = hiltViewModel()
            val user = viewModel.getUser(userId)
            RemoteUserDetailView(user = user)
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@FlowPreview
@ExperimentalAnimationApi
@InternalCoroutinesApi
@Composable
private fun AppBottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Log.d("$$$", "currentRoute: $currentRoute")
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start to avoid build up a large stack of destinations
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