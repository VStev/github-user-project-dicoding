package com.submission.githubuser.di

import com.submission.githubuser.domain.usecase.GithubUserUseCase
import com.submission.githubuser.domain.usecase.RepoInteractor
import com.submission.githubuser.viewmodelproviders.FavouritesViewModel
import com.submission.githubuser.viewmodelproviders.MainViewModel
import com.submission.githubuser.viewmodelproviders.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val useCaseModules = module {
    factory<GithubUserUseCase> { RepoInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
    viewModel { FavouritesViewModel(get()) }
}