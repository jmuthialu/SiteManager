package com.jay.sitemanager.Domain

import com.jay.sitemanager.DataModels.RemoteUser
import retrofit2.http.GET

interface UserApiInterface {
    @GET("users")
    suspend fun getRemoteUsers(): List<RemoteUser>

}