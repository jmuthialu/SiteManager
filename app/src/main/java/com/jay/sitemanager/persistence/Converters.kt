package com.jay.sitemanager.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jay.sitemanager.dataModels.Address
import com.jay.sitemanager.dataModels.Company

class Converters {

    @TypeConverter
    fun fromAddressToString(adress: Address?): String? {
        val type = object : TypeToken<Address>() {}.type
        return Gson().toJson(adress, type)
    }

    @TypeConverter
    fun fromStringToAddresss(value: String?): Address? {
        val type = object : TypeToken<Address>() {}.type
        return Gson().fromJson<Address>(value, type)
    }

    @TypeConverter
    fun fromCompanyToString(company: Company?): String? {
        val type = object : TypeToken<Company>() {}.type
        return Gson().toJson(company, type)
    }

    @TypeConverter
    fun fromStringToCompany(value: String?): Company? {
        val type = object : TypeToken<Company>() {}.type
        return Gson().fromJson<Company>(value, type)
    }


}