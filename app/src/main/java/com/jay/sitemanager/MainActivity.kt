package com.jay.sitemanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.navigationInfrastructure.scaffolds.AppFrameView
import com.jay.sitemanager.ui.theme.SiteManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var bleFacade: BLEFacade

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d(AppConstants.TAG, "permission grant status: $isGranted")
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
                    AppFrameView(context = context, bleFacade = bleFacade)
                }
            }
        }
    }
}