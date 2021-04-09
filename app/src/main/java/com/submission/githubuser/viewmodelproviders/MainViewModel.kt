package com.submission.githubuser.viewmodelproviders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<List<SimpleUserData>>()
    private val searchResult = MutableLiveData<SearchUserData>()
    private val retrofit = RetrofitInteractable()

    fun fetchUser(){
        val fetch = retrofit.fetchUserFromGitHub()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchUsers()
        call.enqueue(object: Callback<List<SimpleUserData>> {
            override fun onResponse(call: Call<List<SimpleUserData>>, response: Response<List<SimpleUserData>>) {
                if (response.code() == 200){
                    listUsers.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<SimpleUserData>>?, t: Throwable?) {
            }

        })
    }

    fun fetchFollows(argument: Int?, username: String?){
        val fetch = retrofit.fetchUserFromGitHub()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = if (argument == 0) username?.let { service.fetchUserFollowers(it) } else username?.let { service.fetchUsersFollowing(it) }
        call?.enqueue(object: Callback<List<SimpleUserData>> {
            override fun onResponse(call: Call<List<SimpleUserData>>, response: Response<List<SimpleUserData>>) {
                if (response.code() == 200){
                    listUsers.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<SimpleUserData>>?, t: Throwable?) {
                listUsers.value = null
            }
        })
    }

    fun fetchUserSearches(query: String){
        val fetch = retrofit.fetchUserFromGitHub()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchUserByID(query)
        call.enqueue(object: Callback<SearchUserData> {
            override fun onResponse(call: Call<SearchUserData>, response: Response<SearchUserData>) {
                if (response.code() == 200){
                    searchResult.value = response.body()
                    val temp: SearchUserData? = searchResult.value
                    if (temp != null) {
                        listUsers.value = temp.items
                    }
                }
            }

            override fun onFailure(call: Call<SearchUserData>, t: Throwable?) {
                Log.d("ERROR SEARCH", t.toString())
            }
        })
    }

    fun getUsers(): LiveData<List<SimpleUserData>> {
        return listUsers
    }

    fun getFollows(): LiveData<List<SimpleUserData>>{
        return listUsers
    }

    fun getSearchResults(): LiveData<List<SimpleUserData>>{
        return listUsers
    }

}