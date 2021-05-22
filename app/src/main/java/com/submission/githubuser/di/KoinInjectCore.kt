package com.submission.githubuser.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.submission.githubuser.databaseapi.AppDatabase
import com.submission.githubuser.repository.GithubUserRepository
import com.submission.githubuser.repository.LocalDataSource
import com.submission.githubuser.repository.RemoteDataSource
import com.submission.githubuser.utility.AppExecutors
import com.submission.githubuser.webapi.RetrofitInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@JvmField
val dbModule = module {
    factory { get<AppDatabase>().userDAO() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "users"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val netModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        retrofit.create(RetrofitInterface::class.java)
    }
}

val repoModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single { GithubUserRepository(get(), get(), get()) }
}