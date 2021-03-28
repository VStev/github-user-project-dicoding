package com.submission.githubuser.webapi

import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.user.UserData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @GET("/users")
    @Headers("Authorization: token 57e4d9992571492c93d2a62398a829c0b65ea978")
    fun fetchUsers(): Call<ArrayList<SimpleUserData>>

    @GET("/users/{user}")
    @Headers("Authorization: token 57e4d9992571492c93d2a62398a829c0b65ea978")
    fun fetchProfile(@Path("user") username: String): Call<UserData>

    @GET("/users/{user}/followers")
    @Headers("Authorization: token 57e4d9992571492c93d2a62398a829c0b65ea978")
    fun fetchUserFollowers(@Path("user") username: String): Call<ArrayList<SimpleUserData>>

    @GET("/users/{user}/following")
    @Headers("Authorization: token 57e4d9992571492c93d2a62398a829c0b65ea978")
    fun fetchUsersFollowing(@Path("user") username: String): Call<ArrayList<SimpleUserData>>

    @GET("/search/users")
    @Headers("Authorization: token 57e4d9992571492c93d2a62398a829c0b65ea978")
    fun fetchUserByID(@Query("q") username: String): Call<SearchUserData>
}