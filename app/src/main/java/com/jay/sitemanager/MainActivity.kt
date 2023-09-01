package com.jay.sitemanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.presentation.BLEListView
import com.jay.sitemanager.presentation.BLEListViewModel
import com.jay.sitemanager.presentation.UserListViewModel
import com.jay.sitemanager.presentation.UsersListView
import com.jay.sitemanager.ui.theme.SiteManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var bleFacade: BLEFacade

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d("$$$", "permission grant status: $isGranted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SiteManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    bleFacade = BLEFacade(this)
                    requestPermissionLauncher.launch(
                        android.Manifest.permission.BLUETOOTH_SCAN)

                    val context = getApplication().applicationContext
                    SiteManagerNavigation(context = context, bleFacade = bleFacade)
                }
            }
        }
    }
}

@Composable
fun SiteManagerNavigation(context: Context, bleFacade: BLEFacade) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "bleList") {

        composable(route = "usersList") {
            val viewModel: UserListViewModel = hiltViewModel()
            viewModel.getRemoteUsers()
            UsersListView(usersState = viewModel.usersState.value)
        }

        composable(route = "bleList") {
            val bleViewModel: BLEListViewModel = hiltViewModel()
            bleViewModel.bleFacade = bleFacade
            bleViewModel.startScan()
            BLEListView(bleDevices = bleViewModel.bleDevices)
        }
    }
}