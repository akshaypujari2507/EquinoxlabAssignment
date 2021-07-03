package com.equinoxlab.data.remote.api

import com.equinoxlab.data.remote.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("getManager")
    fun getManager(): Call<ApiResponse>

}