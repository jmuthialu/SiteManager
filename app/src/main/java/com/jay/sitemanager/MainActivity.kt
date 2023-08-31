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
import com.jay.sitemanager.presentation.UserListViewModel
import com.jay.sitemanager.presentation.UsersListView
import com.jay.sitemanager.ui.theme.SiteManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var bleFacade: BLEFacade? = null

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("$$$", "permission granted")
                bleFacade?.startScan()
            } else {
                Log.d("$$$", "permission NOT granted")
            }
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
                    SiteManagerNavigation(context = context)
                }
            }
        }
    }
}

@Composable
fun SiteManagerNavigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "usersList") {
        composable(route = "usersList") {
            val viewModel: UserListViewModel = hiltViewModel()
            viewModel.getLocalUsers(context = context)
            UsersListView(usersState = viewModel.usersState.value)
        }
    }
}