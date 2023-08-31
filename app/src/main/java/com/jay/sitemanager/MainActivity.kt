package com.jay.sitemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jay.sitemanager.presentation.UserListViewModel
import com.jay.sitemanager.presentation.UsersListView
import com.jay.sitemanager.ui.theme.SiteManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SiteManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SiteManagerNavigation()
                }
            }
        }
    }
}

@Composable
fun SiteManagerNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "usersList") {
        composable(route = "usersList") {
            val viewModel: UserListViewModel = hiltViewModel()
            viewModel.getUsers()
            UsersListView(usersState = viewModel.usersState.value)
        }
    }
}