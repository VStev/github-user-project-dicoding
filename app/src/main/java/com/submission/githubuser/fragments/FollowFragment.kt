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
import com.submission.githubuser.databinding.FragmentFollowBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {

    private lateinit var recycleView: RecyclerView
    private val retrofit = RetrofitInteractable()
    private var binding: FragmentFollowBinding? = null
    private val viewBind  get() = binding!!

    companion object {
        private const val ARG_FOLLOW_FOLLOWING = "follow_following"
        @JvmStatic
        fun newInstance(index: Int) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FOLLOW_FOLLOWING, index)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        showLoading(true)
        showLayout()
    }

    private fun showLayout() {
        val argument = arguments?.getInt(ARG_FOLLOW_FOLLOWING, 0)
        val fetch = retrofit.fetchUserFromGitHub()
        val dataAdapter = CardViewUserAdapter()
        val username = this.activity?.intent?.getStringExtra(EXTRA_USER)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = dataAdapter
        val service = fetch.create(RetrofitInterface::class.java)
        val call = if (argument == 0) username?.let { service.fetchUserFollowers(it) } else username?.let { service.fetchUsersFollowing(it) }
        //CALLS GET -> api.github.com/users/{username}/following or api.github.com/users/{username}/followers depends on argument
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