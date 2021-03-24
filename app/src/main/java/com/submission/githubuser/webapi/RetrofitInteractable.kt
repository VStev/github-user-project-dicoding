package com.submission.githubuser.webapi

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInteractable { //YES I KNOW THE NAME SUCKS JUST GET ON WITH IT

    companion object{
        private const val baseURL = "https://api.github.com"
    }

    fun fetchUserFromGitHub(): Retrofit{ //THIS THING WILL BE USED BOTH FOR USER LIST AND USER DATA BECAUSE I CANT FIGURE ANYTHING ELSE
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }
}