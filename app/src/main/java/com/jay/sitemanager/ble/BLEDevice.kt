package com.jay.sitemanager.ble

data class BLEDevice(
    val address: String?,
    val serviceUUID: String?,
    var rssi: Int?
)
