package com.jay.sitemanager.dataModels

data class BLEDevice(
    val address: String?,
    val serviceUUID: String?,
    var rssi: Int?
)
