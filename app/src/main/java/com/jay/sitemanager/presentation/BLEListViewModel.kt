package com.jay.sitemanager.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var bleFacade: BLEFacade? = null
    val _bleDevices = mutableStateOf(emptyList<BLEDevice>())
    val bleDevices: State<List<BLEDevice>>
        get() = _bleDevices
    var timer: Timer? = null

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        Log.d("SM", "errorHandler: ${exception.message}"   )
    }
    fun startScan() {
        bleFacade?.startScan()
        getBLEDevicesOnSchedule()
    }

    fun stopScan() {
        timer?.cancel()
        timer = null
        bleFacade?.stopScan()
        _bleDevices.value = emptyList()
    }

    fun getBLEDevicesOnSchedule () {
        timer = Timer()
        timer?.scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                viewModelScope.launch(errorHandler) {
                    val devices = bleFacade?.getBLEDevices() ?: emptyList()
                    _bleDevices.value = emptyList()
                    _bleDevices.value = devices
                    Log.d("$$$", "bleDevices in viewModel: ${_bleDevices.value.size}")
                }
            }
        }, 1000, 2000)
    }

}