package com.submission.githubuser.webapi

import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.user.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/users")
    fun fetchUsers(): Call<List<SimpleUserData>>

    @GET("/users/{user}")
    fun fetchProfile(@Path("user") username: String): Call<UserData>

    @GET("/users/{user}/followers")
    fun fetchUserFollowers(@Path("user") username: String): Call<List<SimpleUserData>>

    @GET("/users/{user}/following")
    fun fetchUsersFollowing(@Path("user") username: String): Call<List<SimpleUserData>>

    @GET("/search/users")
    fun fetchUserByID(@Query("q") username: String): Call<SearchUserData>
}