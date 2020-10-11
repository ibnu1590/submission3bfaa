package com.example.submission3bfaa.api


import com.example.submission3bfaa.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        }

        val service by lazy {
            val create: ApiService = retrofit.create(ApiService::class.java)
            create
        }
    }
}