package com.submission.githubuser.domain.usecase

import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity
import com.submission.githubuser.webapi.ApiResponse
import io.reactivex.Flowable

interface GithubUserUseCase {
    fun getUsers(): Flowable<ApiResponse<List<SimpleUserDataEntity>>>

    fun getFollower(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>>

    fun getFollowing(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>>

    fun searchUser(username: String): Flowable<ApiResponse<SearchUserData>>

    fun getProfile(username: String): Flowable<ApiResponse<UserDataEntity>>

    fun getAllFavUser(): Flowable<List<SimpleUserDataEntity>>

    fun findUserById(user: String): Flowable<List<SimpleUserDataEntity>>

    fun insert(user: SimpleUserDataEntity)

    fun delete(user: String)
}