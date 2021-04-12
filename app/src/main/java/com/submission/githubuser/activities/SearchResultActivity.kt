package com.submission.githubuser.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.databinding.ActivitySearchResultBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.viewmodelproviders.MainViewModel

class SearchResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "query"
    }

    private lateinit var viewBind: ActivitySearchResultBinding
    private lateinit var recycleView: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        recycleView = viewBind.userList
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        supportActionBar?.title = getString(R.string.search_result)
        showLoading(true)
        showLayout()
    }

    private fun showLayout() {
        val userID = intent.getStringExtra(EXTRA_QUERY).toString()
        val dataAdapter = CardViewUserAdapter()
        mainViewModel.fetchUserSearches(userID)
        mainViewModel.getSearchResults().observe(this, { SimpleUserData ->
            if (SimpleUserData != null && SimpleUserData.isNotEmpty()){
                dataAdapter.setData(SimpleUserData)
                showLoading(false)
            }else{
                viewBind.userList.visibility = View.GONE
                viewBind.constraintLayout.visibility = View.VISIBLE
                showLoading(false)
            }
        })
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter
        dataAdapter.setOnItemClickCallback(object: CardViewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: String?) {
                val toUserDetails = Intent(this@SearchResultActivity, UserDetailActivity::class.java)
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
}