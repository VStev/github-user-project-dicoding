package com.submission.githubuser.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.submission.githubuser.domain.usecase.GithubUserUseCase
import com.submission.githubuser.user.SimpleUserDataEntity

class FavouritesViewModel(private val useCase: GithubUserUseCase) : ViewModel() {

    fun getFav(user: String): LiveData<List<SimpleUserDataEntity>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.findUserById(user))
    }

    fun getAllFav(): LiveData<List<SimpleUserDataEntity>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getAllFavUser())
    }

    fun insertToFavorite(user: String, avatar: String){
        useCase.insert(
            SimpleUserDataEntity(user, avatar)
        )
    }

    fun deleteFromFavourites( user: String){
        useCase.delete(user)
    }
}