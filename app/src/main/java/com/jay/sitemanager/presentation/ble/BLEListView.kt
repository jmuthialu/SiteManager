package com.jay.sitemanager.presentation.ble

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jay.sitemanager.AppConstants
import com.jay.sitemanager.R
import com.jay.sitemanager.dataModels.BLEDevice
import com.jay.sitemanager.presentation.components.IconRenderer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEListView(viewModel: BLEListViewModel, topModifier: Modifier, bottomModifier: Modifier) {

    Column(modifier = topModifier) {

        BLEScanButtonsView(viewModel = viewModel)

        if (viewModel.bleDevices.value.isNotEmpty()) {
            LazyColumn(
                modifier = bottomModifier,
                contentPadding = PaddingValues(
                    vertical = 10.dp,
                    horizontal = 10.dp
                )
            ) {
                Log.d(
                    AppConstants.TAG,
                    "bleDevices in view: ${viewModel.bleDevices.value.size}"
                )
                items(viewModel.bleDevices.value) { bleDevice ->
                    BLEDeviceItem(bleDevice)
                }
            }
        } else {
            if (viewModel.scanStarted.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
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
            Icon(
                painter = painterResource(id = R.drawable.bluetooth),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
            BLECell(address = item.address,
                rssi = item.rssi,
                modifier = Modifier.weight(0.85f))
        }

    }
}

@Composable
fun BLECell(address: String?, rssi: Int?, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = (address ?: "no address" ), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.rssi),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(text = (rssi.toString() ?: "no rssi"), style = MaterialTheme.typography.bodySmall)
        }
    }
}