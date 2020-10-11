package com.example.submission3bfaa.api

import com.example.submission3bfaa.BuildConfig
import com.example.submission3bfaa.utils.SearchResponse
import com.example.submission3bfaa.utils.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:" + BuildConfig.TOKEN)
    fun getSearchResult(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}