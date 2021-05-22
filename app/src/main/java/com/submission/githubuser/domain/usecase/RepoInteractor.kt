package com.submission.githubuser.domain.usecase

import com.submission.githubuser.repository.GithubUserRepository
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity
import com.submission.githubuser.webapi.ApiResponse
import io.reactivex.Flowable

class RepoInteractor(private val repository: GithubUserRepository): GithubUserUseCase {

    override fun getUsers(): Flowable<ApiResponse<List<SimpleUserDataEntity>>> = repository.getUsers()

    override fun getFollower(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> = repository.getFollower(username)

    override fun getFollowing(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> = repository.getFollowing(username)

    override fun searchUser(username: String): Flowable<ApiResponse<SearchUserData>> = repository.searchUser(username)

    override fun getProfile(username: String): Flowable<ApiResponse<UserDataEntity>> = repository.getProfile(username)

    override fun getAllFavUser(): Flowable<List<SimpleUserDataEntity>> = repository.getAllFavUser()

    override fun findUserById(user: String): Flowable<List<SimpleUserDataEntity>> = repository.findUserById(user)

    override fun insert(user: SimpleUserDataEntity) {
        repository.insert(user)
    }

    override fun delete(user: String) {
        repository.delete(user)
    }
}