package com.jay.sitemanager.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteUser(
    @PrimaryKey()
    val id: Int?,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val website: String?,
    val address: Address?,
    val company: Company?
) {
    fun getAddressString(): String {
        return "${address?.street}, ${address?.suite}, ${address?.city}, ${address?.zipcode}"
    }

    fun getCompanyString(): String {
        return "${company?.name}, ${company?.catchPhrase}, ${company?.bs}"
    }
}

data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: Geo?
)

data class Geo (
    val lat: String?,
    val lng: String?
)

data class Company(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)
