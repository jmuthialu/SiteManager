package com.jay.sitemanager.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import java.util.HashMap

class BLEFacade(context: Context) {

    companion object {
        const val TAG = "$$$"
    }

    private val scanResultDictionary: MutableMap<String, ScanResult> = HashMap()
    private val bleDevices = mutableListOf<BLEDevice>()

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager.adapter
    }
    private var bleScanner: BluetoothLeScanner? = null
    private var scanFilters: List<ScanFilter> = listOf()
    private var scanSettings: ScanSettings = ScanSettings.Builder().build()

    init {
        if (bluetoothAdapter?.bluetoothLeScanner != null) {
            Log.d(TAG, "BLEFacade: bluetoothLeScanner is NOT null")
            bleScanner = bluetoothAdapter?.bluetoothLeScanner
            scanSettings = bleScanSettings()
            Log.d(TAG, "isBluetoothRadioEnabled: ${isBluetoothRadioEnabled()}")
//            startScan()

        } else {
            Log.d(TAG, "BLEFacade: bluetoothLeScanner is null")
        }
    }

    private fun bleScanSettings(): ScanSettings {
        val scanSettingsBuilder = ScanSettings.Builder()
        scanSettingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        return scanSettingsBuilder.build()
    }

    // Checks if BLE radio at phone level is turned on
    // bluetoothLeScanner will be null if the radio is turned OFF
    fun isBluetoothRadioEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        Log.d(TAG, "startScan called")
        bleScanner?.startScan(scanFilters, scanSettings, bleScanCallback)
    }

//    fun stopScan() {
//        bleScanner?.stopScan(bleScanCallback)
//    }

    private val bleScanCallback: ScanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val address = result.device.address
            val serviceUUID = result.scanRecord?.serviceUuids?.first().toString()
            val rssi = result.rssi
            if  (!scanResultDictionary.containsKey(address)) {
                scanResultDictionary[address] = result
                val bleDevice = BLEDevice(address, serviceUUID, rssi)
                bleDevices.add(bleDevice)
                Log.d(TAG, "$$$ BLE Device added: $bleDevice - Total count: ${bleDevices.size}")
            } else {
                bleDevices.filter { it.address == address }.forEach {
                    it.rssi = rssi
                    Log.d(TAG, "$$$ BLE Device updated: $it")
                }
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            Log.d(TAG, "onBatchScanResults called, with " + results.size)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(TAG, "onScanFailed with error code: $errorCode")
            super.onScanFailed(errorCode)
        }
    }


}