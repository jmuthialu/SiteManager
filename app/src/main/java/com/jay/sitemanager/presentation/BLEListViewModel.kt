package com.jay.sitemanager.presentation

import android.util.Log
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
class BLEListViewModel @Inject constructor (
    private val userRepository: UserRepository
): ViewModel() {

    var bleFacade: BLEFacade? = null
    val _bleDevices = mutableStateOf(emptyList<BLEDevice>())
    val bleDevices = _bleDevices
    var timer: Timer? = null
    var toggle = true

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        Log.d("SM", "errorHandler: ${exception.message}"   )
    }
    fun startScan() {
        bleFacade?.startScan()
//        val devices = bleFacade?.getBLEDevices() ?: emptyList()
//        _bleDevices.value = devices
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
                viewModelScope.launch(errorHandler) {
                    val devices = bleFacade?.getBLEDevices() ?: emptyList()
                    Log.d(
                        "$$$",
                        "bleDevices in vieModel: ${_bleDevices.value.size} - ${Thread.currentThread()}")
                    _bleDevices.value = emptyList()
                    _bleDevices.value = devices
                }

            }
        }, 1000, 1000)
    }

}