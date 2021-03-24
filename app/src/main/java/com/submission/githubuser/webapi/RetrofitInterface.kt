package com.submission.githubuser.webapi

import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.user.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/users")
    fun fetchUsers(): Call<List<SimpleUserData>>

    @GET("/users/{user}")
    fun fetchProfile(@Query("user") username: String): Call<UserData>
}