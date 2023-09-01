package com.jay.sitemanager.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.jay.sitemanager.dataModels.BLEDevice
import java.util.HashMap
import javax.inject.Singleton

class BLEFacade(private val context: Context) {
    companion object {
        const val TAG = "SBD"
    }

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager.adapter
    }
    private var bleScanner: BluetoothLeScanner? = null
    private var scanFilters: List<ScanFilter> = listOf()
    private var scanSettings: ScanSettings = ScanSettings.Builder().build()
    private val scanResultDictionary: MutableMap<String, ScanResult> = HashMap()
    private val bleDevices = mutableListOf<BLEDevice>()

    init {
        if (bluetoothAdapter?.bluetoothLeScanner != null) {
            bleScanner = bluetoothAdapter?.bluetoothLeScanner
            scanSettings = bleScanSettings()
            Log.d(TAG, "isBluetoothRadioEnabled: ${isBluetoothRadioEnabled()}")
        } else {
            Log.d(TAG, "BLEFacade: bluetoothLeScanner is null")
        }
    }

    private fun bleScanSettings(): ScanSettings {
        val scanSettingsBuilder = ScanSettings.Builder()
        scanSettingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        return scanSettingsBuilder.build()
    }

    private fun isBluetoothRadioEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    fun getBLEDevices(): List<BLEDevice> {
        return bleDevices
    }

    fun startScan() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "startScan...")
            bleScanner?.startScan(scanFilters, scanSettings, bleScanCallback)
        }
    }

    fun stopScan() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "stopScan...")
            bleScanner?.stopScan(bleScanCallback)
        }
    }

    val bleScanCallback: ScanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val address = result.device.address
            val serviceUUID = result.scanRecord?.serviceUuids?.first().toString()
            val rssi = result.rssi

            // Add only unique devices
            if  (!scanResultDictionary.containsKey(address)) {
                scanResultDictionary[address] = result
                val bleDevice = BLEDevice(address, serviceUUID, rssi)
                bleDevices.add(bleDevice)
                Log.d(TAG, "$$$ BLE Device added: $bleDevice - Total count: ${bleDevices.size}")
            } else {
                // Update RSSI if device already exists
                bleDevices.filter { it.address == address }.forEach {
                    it.rssi = rssi
                    Log.d(TAG, "$$$ BLE Device updated: $it")
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(TAG, "onScanFailed with error code: $errorCode")
            super.onScanFailed(errorCode)
        }
    }

}