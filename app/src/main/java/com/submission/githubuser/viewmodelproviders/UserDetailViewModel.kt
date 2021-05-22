package com.submission.githubuser.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.submission.githubuser.domain.usecase.GithubUserUseCase
import com.submission.githubuser.user.UserDataEntity
import com.submission.githubuser.webapi.ApiResponse

class UserDetailViewModel(private val useCase: GithubUserUseCase) : ViewModel() {

    fun getDetail(user: String): LiveData<ApiResponse<UserDataEntity>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getProfile(user))
    }

}