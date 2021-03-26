package com.submission.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.activities.UserDetailActivity
import com.submission.githubuser.databinding.FragmentHomeBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.viewmodelproviders.MainViewModel

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewBind get() = binding!!
    private lateinit var recycleView: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        showLoading(true)
        showLayout(view)
    }

    private fun showLayout(view: View) {
        val dataAdapter = CardViewUserAdapter()
        mainViewModel.fetchUser()
        recycleView.layoutManager = LinearLayoutManager(view.context)
        recycleView.adapter = dataAdapter
        mainViewModel.getUsers().observe(viewLifecycleOwner, {SimpleUserData ->
            if (SimpleUserData != null){
                dataAdapter.setData(SimpleUserData)
                showLoading(false)
            }
        })
        dataAdapter.setOnItemClickCallback(object: CardViewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: String?) {
                val toUserDetails = Intent(activity, UserDetailActivity::class.java)
                toUserDetails.putExtra(UserDetailActivity.EXTRA_USER, user)
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}