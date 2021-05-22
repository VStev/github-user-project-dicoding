package com.submission.githubuser.webapi

import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/users")
    fun fetchUsers(): Flowable<List<SimpleUserDataEntity>>

    @GET("/users/{user}")
    fun fetchProfile(@Path("user") username: String): Flowable<UserDataEntity>

    @GET("/users/{user}/followers")
    fun fetchUserFollowers(@Path("user") username: String): Flowable<List<SimpleUserDataEntity>>

    @GET("/users/{user}/following")
    fun fetchUsersFollowing(@Path("user") username: String): Flowable<List<SimpleUserDataEntity>>

    @GET("/search/users")
    fun fetchUserByID(@Query("q") username: String): Flowable<SearchUserData>
}