package com.submission.githubuser.repository

import com.submission.githubuser.domain.repository.InterfaceGithubUserRepo
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity
import com.submission.githubuser.utility.AppExecutors
import com.submission.githubuser.webapi.ApiResponse
import io.reactivex.Flowable

class GithubUserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val executor: AppExecutors
): InterfaceGithubUserRepo {

    override fun getUsers(): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        return remoteDataSource.getAll()
    }

    override fun getFollower(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        return remoteDataSource.getFollower(username)
    }

    override fun getFollowing(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        return remoteDataSource.getFollowing(username)
    }

    override fun searchUser(username: String): Flowable<ApiResponse<SearchUserData>> {
        return remoteDataSource.searchUser(username)
    }

    override fun getProfile(username: String): Flowable<ApiResponse<UserDataEntity>> {
        return remoteDataSource.getProfile(username)
    }

    override fun getAllFavUser(): Flowable<List<SimpleUserDataEntity>> {
        return localDataSource.getAllUser()
    }

    override fun findUserById(user: String): Flowable<List<SimpleUserDataEntity>> {
        return localDataSource.findUserById(user)
    }

    override fun insert(user: SimpleUserDataEntity) {
        executor.diskIO().execute{ localDataSource.insert(user) }
    }

    override fun delete(user: String) {
        executor.diskIO().execute{ localDataSource.delete(user) }
    }
}