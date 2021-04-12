package com.submission.githubuser.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuser.user.UserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private var userDetail = MutableLiveData<UserData>()
    private val retrofit = RetrofitInteractable()

    fun fetchData(user: String){
        val fetch = retrofit.fetchUserFromGitHub()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchProfile(user) //CALLS GET -> api.github.com/users/username
        call.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>?, response: Response<UserData>?) {
                if (response?.code() == 200) {
                    userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserData>?, t: Throwable?) {
                userDetail.value = null
            }

        })
    }

    fun getDetail(): LiveData<UserData> {
        return userDetail
    }

}