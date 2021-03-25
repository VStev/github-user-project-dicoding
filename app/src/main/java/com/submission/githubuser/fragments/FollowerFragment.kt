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
import com.submission.githubuser.databinding.FragmentFollowerBinding
import com.submission.githubuser.databinding.FragmentHomeBinding
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
    private var binding: FragmentFollowerBinding? = null
    private val viewBind  get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = viewBind.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        showLoading(true)
        showLayout()
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
                    showLoading(false)
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

    private fun showLoading(state: Boolean) {
        if (state) {
            viewBind.progressBar.visibility = View.VISIBLE
        } else {
            viewBind.progressBar.visibility = View.GONE
        }
    }
}