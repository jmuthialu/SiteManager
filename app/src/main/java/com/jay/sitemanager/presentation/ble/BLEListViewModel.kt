package com.jay.sitemanager.presentation.ble

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.sitemanager.AppConstants
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.dataModels.BLEDevice
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.concurrent.scheduleAtFixedRate

@HiltViewModel
class BLEListViewModel @Inject constructor (): ViewModel() {
    companion object {
        // Refreshes BLE device list every second from BLEFacade
        private const val refreshDuration = 1000L
    }

    var bleFacade: BLEFacade? = null

    val _bleDevices = mutableStateOf(emptyList<BLEDevice>())
    val bleDevices: State<List<BLEDevice>>
        get() = _bleDevices

    val _scanStarted = mutableStateOf(false)
    val scanStarted: State<Boolean>
        get() = _scanStarted

    var refreshTimer: Timer? = null

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        Log.d(AppConstants.TAG, "errorHandler: ${exception.message}"   )
    }

    fun startScan() {
        if (refreshTimer == null) {
            _scanStarted.value = true
            bleFacade?.startScan()
            getBLEDevicesOnSchedule()
        }
    }

    fun stopScan() {
        _scanStarted.value = false
        refreshTimer?.cancel()
        refreshTimer = null
        bleFacade?.stopScan()
        _bleDevices.value = emptyList()
    }

    // BLE Scan runs every few milli seconds but UI is not recommened to update that often
    // To improve UI performance, BLE Scan is run on a schedule and is updated every second.
    // This also updates RSSI values for all BLE devices.
    fun getBLEDevicesOnSchedule () {
        refreshTimer = Timer()
        refreshTimer?.scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                viewModelScope.launch(errorHandler) {
                    val devices = bleFacade?.getBLEDevices() ?: emptyList()
                    _bleDevices.value = emptyList()
                    _bleDevices.value = devices
                    Log.d(AppConstants.TAG, "bleDevices in viewModel: ${_bleDevices.value.size}")
                }
            }
        }, refreshDuration, refreshDuration)
    }

}