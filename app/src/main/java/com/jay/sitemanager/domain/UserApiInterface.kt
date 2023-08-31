package com.jay.sitemanager.domain

import com.jay.sitemanager.dataModels.RemoteUser
import retrofit2.http.GET

interface UserApiInterface {
    @GET("users")
    suspend fun getRemoteUsers(): List<RemoteUser>

}