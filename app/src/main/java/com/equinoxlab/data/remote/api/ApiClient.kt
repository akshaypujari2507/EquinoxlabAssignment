package com.equinoxlab.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val BASE_URL = "http://karma.equinoxlab.com/betaDailyUpdateApi/Service1.svc/"

    private val client = OkHttpClient.Builder()
        .build()

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(ApiService::class.java)!!

}
