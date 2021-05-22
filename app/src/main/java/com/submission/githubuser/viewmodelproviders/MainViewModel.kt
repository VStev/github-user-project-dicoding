package com.submission.githubuser.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.submission.githubuser.domain.usecase.GithubUserUseCase
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.webapi.ApiResponse

class MainViewModel(private val useCase: GithubUserUseCase) : ViewModel() {

    fun getUsers(): LiveData<ApiResponse<List<SimpleUserDataEntity>>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getUsers())
    }

    fun getFollows(argument: Int, username: String): LiveData<ApiResponse<List<SimpleUserDataEntity>>>{
        return when (argument){
            0 -> LiveDataReactiveStreams.fromPublisher(useCase.getFollower(username))
            else -> LiveDataReactiveStreams.fromPublisher(useCase.getFollowing(username))
        }
    }

    fun getSearchResults(query: String): LiveData<ApiResponse<SearchUserData>>{
        return LiveDataReactiveStreams.fromPublisher(useCase.searchUser(query))
    }

}