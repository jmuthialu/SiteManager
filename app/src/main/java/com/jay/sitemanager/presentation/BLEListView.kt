package com.jay.sitemanager.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.dataModels.BLEDevice
import com.jay.sitemanager.dataModels.RemoteUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEListView(viewModel: BLEListViewModel, bottomModifier: Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "BLE Devices") }
            )
        }
    ) { topPadding ->
        Column(modifier = Modifier.padding(topPadding)) {
            Row (
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )  {
                Button(onClick = {
                    viewModel.startScan()
                }) {
                    Text(text = "Start Scan")
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Button(onClick = {
                    viewModel.stopScan()
                }) {
                    Text(text = "Stop Scan")
                }
            }

            LazyColumn(
                modifier = bottomModifier,
                contentPadding = PaddingValues (
                    vertical = 10.dp,
                    horizontal = 10.dp
                )
            ) {
                Log.d("$$$", "bleDevices in view: ${viewModel.bleDevices.value.size}")
                items(viewModel.bleDevices.value) { bleDevice ->
                    BLEDeviceItem(bleDevice)
                }
            }
        }
    }
}

@Composable
fun BLEDeviceItem(item: BLEDevice) {
    Card(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            BLEIcon(image = Icons.Default.AccountBox , modifier = Modifier.weight(0.15f))
            BLECell(address = item.address,
                rssi = item.rssi,
                modifier = Modifier.weight(0.85f))
        }

    }
}

@Composable
fun BLEIcon(image: ImageVector, modifier: Modifier) {
    Image(
        imageVector = image,
        contentDescription = "Restaurant icon",
        modifier = modifier
    )
}

@Composable
fun BLECell(address: String?, rssi: Int?, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = (address ?: "no address" ), style = MaterialTheme.typography.bodyMedium)
        Text(text = (rssi.toString() ?: "no rssi"), style = MaterialTheme.typography.bodySmall)
    }
}