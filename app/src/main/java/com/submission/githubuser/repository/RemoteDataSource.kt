package com.submission.githubuser.repository

import android.annotation.SuppressLint
import android.util.Log
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity
import com.submission.githubuser.webapi.ApiResponse
import com.submission.githubuser.webapi.RetrofitInterface
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource (private val retrofit: RetrofitInterface) {

    @SuppressLint("CheckResult")
    fun getAll(): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        val resultData = PublishSubject.create<ApiResponse<List<SimpleUserDataEntity>>>()
        val client = retrofit.fetchUsers()
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultData.onNext(if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getFollower(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        val resultData = PublishSubject.create<ApiResponse<List<SimpleUserDataEntity>>>()
        val client = retrofit.fetchUserFollowers(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultData.onNext(if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getFollowing(username: String): Flowable<ApiResponse<List<SimpleUserDataEntity>>> {
        val resultData = PublishSubject.create<ApiResponse<List<SimpleUserDataEntity>>>()
        val client = retrofit.fetchUsersFollowing(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultData.onNext(if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
    @SuppressLint("CheckResult")
    fun searchUser(username: String): Flowable<ApiResponse<SearchUserData>> {
        val resultData = PublishSubject.create<ApiResponse<SearchUserData>>()
        val client = retrofit.fetchUserByID(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                val dataArray = response.items
                if (dataArray != null) {
                    resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
                }
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }


    @SuppressLint("CheckResult")
    fun getProfile(username: String): Flowable<ApiResponse<UserDataEntity>> {
        val resultData = PublishSubject.create<ApiResponse<UserDataEntity>>()
        val client = retrofit.fetchProfile(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}