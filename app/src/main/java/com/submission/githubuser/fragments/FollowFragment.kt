package com.submission.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.activities.UserDetailActivity
import com.submission.githubuser.activities.UserDetailActivity.Companion.EXTRA_USER
import com.submission.githubuser.databinding.FragmentFollowBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.viewmodelproviders.MainViewModel
import com.submission.githubuser.webapi.ApiResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment() {

    private lateinit var recycleView: RecyclerView
    private var binding: FragmentFollowBinding? = null
    private val viewBind get() = binding
    private val mainViewModel: MainViewModel by viewModel()

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FrameLayout? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return viewBind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        showLoading(true)
        showLayout()
    }

    private fun showLayout() {
        val argument = arguments?.getInt(ARG_FOLLOW_FOLLOWING, 0) as Int
        val username = this.activity?.intent?.getStringExtra(EXTRA_USER) as String
        val dataAdapter = CardViewUserAdapter()
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = dataAdapter
        mainViewModel.getFollows(argument, username).observe(viewLifecycleOwner, {response ->
            if (response != null){
                when (response){
                    is ApiResponse.Success -> {
                        dataAdapter.setData(response.data)
                        showLoading(false)
                    }
                    is ApiResponse.Empty -> {
                        viewBind?.userList?.visibility = View.GONE
                        viewBind?.constraintLayout?.visibility = View.VISIBLE
                        showLoading(false)
                    }
                    is ApiResponse.Error -> {
                        viewBind?.userList?.visibility = View.GONE
                        viewBind?.constraintLayout?.visibility = View.VISIBLE
                        showLoading(false)
                    }
                }
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
            viewBind?.progressBar?.visibility = View.VISIBLE
        } else {
            viewBind?.progressBar?.visibility = View.GONE
        }
    }
}