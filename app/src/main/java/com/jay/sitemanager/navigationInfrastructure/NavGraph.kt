package com.jay.sitemanager.navigationInfrastructure

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.presentation.ble.BLEListView
import com.jay.sitemanager.presentation.ble.BLEListViewModel
import com.jay.sitemanager.presentation.users.localUsers.LocalUserDetailView
import com.jay.sitemanager.presentation.users.localUsers.LocalUserDetailViewModel
import com.jay.sitemanager.presentation.users.localUsers.LocalUserListViewModel
import com.jay.sitemanager.presentation.users.localUsers.LocalUsersListView
import com.jay.sitemanager.presentation.users.remoteUsers.RemoteUserDetailView
import com.jay.sitemanager.presentation.users.remoteUsers.RemoteUserDetailViewModel
import com.jay.sitemanager.presentation.users.remoteUsers.RemoteUserListViewModel
import com.jay.sitemanager.presentation.users.remoteUsers.RemoteUsersListView


@Composable
fun NavGraph(navController: NavHostController,
             context: Context,
             bleFacade: BLEFacade,
             bottomModifier : Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Bluetooth.route) {

        // Home Screen route
        composable(route = Screen.Bluetooth.route) {
            val bleViewModel: BLEListViewModel = hiltViewModel()
            bleViewModel.bleFacade = bleFacade
            BLEListView(viewModel = bleViewModel, bottomModifier = bottomModifier)
        }

        // Tabbed Remote and Local User route
        composable(route = Screen.User.route) {
            val localUserViewModel: LocalUserListViewModel = hiltViewModel()
            localUserViewModel.getLocalUsers(context = context)
            val remoteUserListViewModel: RemoteUserListViewModel = hiltViewModel()
            remoteUserListViewModel.getRemoteUsers()
            TabView(
                titles = listOf("Local Users", "Remote Users"),
                localUserView = {
                    LocalUsersListView(
                        usersState = localUserViewModel.usersState.value,
                        bottomModifier = bottomModifier
                    ) { id ->
                        navController.navigate(Screen.LocalUserDetail.route + "/$id")
                    }
                },
                remoteUserView = {
                    RemoteUsersListView(
                        usersState = remoteUserListViewModel.usersState.value,
                        bottomModifier = bottomModifier
                    ) { id ->
                        navController.navigate(Screen.RemoteUserDetail.route + "/$id")
                    }
                }
            )
        }

        // Remote User Detail Route
        composable(
            route = Screen.RemoteUserDetail.route + "/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })) { backStackEntry ->
            val userId = backStackEntry.arguments?.get("userId") as Int?
            val viewModel: RemoteUserDetailViewModel = hiltViewModel()
            val user = viewModel.getUser(userId)
//            RemoteUserDetailView(user = user)

            val userName = remember { mutableStateOf("") }
            DetailFrameView(title = userName, onBackClick = { navController.navigateUp() }) { modifier ->
                RemoteUserDetailView(title = userName, user = user, modifier = modifier)
            }

        }

        // Local User Detail Route
        composable(
            route = Screen.LocalUserDetail.route + "/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })) { backStackEntry ->
            val userId = backStackEntry.arguments?.get("userId") as Int?
            val viewModel: LocalUserDetailViewModel = hiltViewModel()
            val user = viewModel.getUser(userId)
            LocalUserDetailView(user = user)
        }
    }
}