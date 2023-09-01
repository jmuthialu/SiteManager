package com.jay.sitemanager.presentation.ble

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BLEScanButtonsView(viewModel: BLEListViewModel) {

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
}