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

    private lateinit var viewBind: ActivityUserDetailBinding
    private lateinit var userID: String
    private lateinit var userDetailViewModel: UserDetailViewModel

    companion object {
        const val EXTRA_USER = "extra user"
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.follower_tab_header, R.string.following_tab_header)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.elevation = 0f
        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        showLoading(true)
        showLayout()
    }

    private fun showLayout(){
        val sectionPageAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = viewBind.viewpager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = viewBind.tabs
        var follower: String
        var following: String
        userID = intent.getStringExtra(EXTRA_USER).toString()
        supportActionBar?.title = userID
        userDetailViewModel.fetchData(userID)
        userDetailViewModel.getDetail().observe(this, { UserData ->
            if (UserData != null){
                val text = "Public Repo : ${UserData.repositoryCount}"
                if (UserData.avatarUrl != ""){
                    Glide.with(this)
                        .load(UserData.avatarUrl)
                        .into(viewBind.circleImageView)
                }else{
                    Glide.with(this)
                        .load(R.drawable.ic_baseline_person_24)
                        .into(viewBind.circleImageView)
                }
                viewBind.name.text = UserData.name
                if (UserData.company != "") viewBind.company.text = UserData.company else viewBind.company.text = "-"
                if (UserData.location != "") viewBind.location.text = UserData.location else viewBind.location.text = "-"
                viewBind.repository.text = text
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
            viewBind.progressBar.visibility = View.VISIBLE
        } else {
            viewBind.progressBar.visibility = View.GONE
        }
    }
}
