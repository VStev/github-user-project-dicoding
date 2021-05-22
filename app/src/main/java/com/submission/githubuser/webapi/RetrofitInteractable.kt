package com.submission.githubuser.webapi

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInteractable { //YES I KNOW THE NAME SUCKS JUST GET ON WITH IT

    fun fetchUserFromGitHub(): Retrofit{ //THIS THING WILL BE USED BOTH FOR USER LIST AND USER DATA BECAUSE I CANT FIGURE ANYTHING ELSE
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }
}