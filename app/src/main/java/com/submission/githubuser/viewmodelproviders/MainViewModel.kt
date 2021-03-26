package com.submission.githubuser.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<SimpleUserData>>()
    private val retrofit = RetrofitInteractable()

    fun fetchUser(){
        val fetch = retrofit.fetchUserFromGitHub()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchUsers() //CALLS GET -> api.github.com/users
        call.enqueue(object: Callback<ArrayList<SimpleUserData>> {
            override fun onResponse(call: Call<ArrayList<SimpleUserData>>, response: Response<ArrayList<SimpleUserData>>) {
                if (response.code() == 200){
                    listUsers.value = response.body()
                }
            }
            override fun onFailure(call: Call<ArrayList<SimpleUserData>>?, t: Throwable?) {
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<SimpleUserData>> {
        return listUsers
    }

}