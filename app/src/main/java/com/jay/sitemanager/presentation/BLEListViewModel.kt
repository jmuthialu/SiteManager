package com.jay.sitemanager.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jay.sitemanager.ble.BLEFacade
import com.jay.sitemanager.dataModels.BLEDevice
import com.jay.sitemanager.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.concurrent.scheduleAtFixedRate

@HiltViewModel
class BLEListViewModel @Inject constructor (
    private val userRepository: UserRepository
): ViewModel() {

    var bleFacade: BLEFacade? = null
    val _bleDevices = mutableStateOf(emptyList<BLEDevice>())
    val bleDevices = _bleDevices
    var timer: Timer? = null

    fun startScan() {
        bleFacade?.startScan()
        getBLEDevicesOnSchedule()
    }

    fun stopScan() {
        timer?.cancel()
        timer = null
        bleFacade?.stopScan()
    }

    fun getBLEDevicesOnSchedule () {
        timer = Timer()
        timer?.scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                val devices = bleFacade?.getBLEDevices() ?: emptyList()
                Log.d("$$$", "devices from ble: $devices")
                _bleDevices.value = devices
            }
        }, 100, 1000)
    }

}