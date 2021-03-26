package com.submission.githubuser.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuser.R
import com.submission.githubuser.databinding.ActivityUserDetailBinding
import com.submission.githubuser.fragments.SectionsPageAdapter
import com.submission.githubuser.viewmodelproviders.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {

    private lateinit var viewbind: ActivityUserDetailBinding
    private lateinit var userID: String
    private lateinit var userDetailViewModel: UserDetailViewModel

    companion object {
        const val EXTRA_USER = "extra user"
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.follower_tab_header, R.string.following_tab_header)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(viewbind.root)
        supportActionBar?.elevation = 0f
        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        showLoading(true)
        showLayout()
    }

    private fun showLayout(){
        val sectionPageAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = viewbind.viewpager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = viewbind.tabs
        var follower: String
        var following: String
        userID = intent.getStringExtra(EXTRA_USER).toString()
        userDetailViewModel.fetchData(userID)
        userDetailViewModel.getDetail().observe(this, { UserData ->
            if (UserData != null){
                val text = "Public Repo : ${UserData.repositoryCount}"
                Glide.with(this)
                    .load(UserData.avatarUrl)
                    .into(viewbind.circleImageView)
                viewbind.name.text = UserData.name
                viewbind.company.text = UserData.company
                viewbind.location.text = UserData.location
                viewbind.repository.text = text
                follower = UserData.followersCount.toString()
                following = UserData.followingCount.toString()
                showLoading(false)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    val tabTitle = if (position == 0) "${resources.getString(TAB_TITLES[position])} ($follower)" else "${resources.getString(TAB_TITLES[position])} ($following)"
                    tab.text = tabTitle
                }.attach()
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            viewbind.progressBar.visibility = View.VISIBLE
        } else {
            viewbind.progressBar.visibility = View.GONE
        }
    }
}
