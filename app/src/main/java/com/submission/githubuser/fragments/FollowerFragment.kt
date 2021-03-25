package com.submission.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.activities.UserDetailActivity
import com.submission.githubuser.activities.UserDetailActivity.Companion.EXTRA_USER
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {

    private lateinit var recycleView: RecyclerView
    private val retrofit = RetrofitInteractable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        showLayout()
        TODO("IMPLEMENT CALL ARGUMENTS BETWEEN FOLLOWING AND FOLLOWER")
    }

    private fun showLayout() {
        val fetch = retrofit.fetchUserFromGitHub()
        val dataAdapter = CardViewUserAdapter()
        val username = this.activity?.intent?.getStringExtra(EXTRA_USER)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = dataAdapter
        val service = fetch.create(RetrofitInterface::class.java)
        val call = username?.let { service.fetchUserFollowers(it) } //CALLS GET -> api.github.com/users
        call?.enqueue(object: Callback<List<SimpleUserData>> {
            override fun onResponse(call: Call<List<SimpleUserData>>, response: Response<List<SimpleUserData>>) {
                if (response.code() == 200){
                    dataAdapter.setData(response.body() as ArrayList<SimpleUserData>)
                    dataAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<SimpleUserData>>?, t: Throwable?) {
            }
        })
        dataAdapter.setOnItemClickCallback(object: CardViewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: String?) {
                val toUserDetails = Intent(activity, UserDetailActivity::class.java)
                toUserDetails.putExtra(EXTRA_USER, user)
                startActivity(toUserDetails)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }
}