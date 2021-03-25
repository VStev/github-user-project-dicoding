package com.submission.githubuser.activities

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuser.R
import com.submission.githubuser.databinding.ActivityUserDetailBinding
import com.submission.githubuser.fragments.SectionsPageAdapter
import com.submission.githubuser.user.UserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var viewbind: ActivityUserDetailBinding
    private lateinit var userID: String
    private val retrofit = RetrofitInteractable()

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
        showLayout()
    }

    private fun showLayout(){
        userID = intent.getStringExtra(EXTRA_USER).toString()
        val fetch = retrofit.fetchUserFromGitHub()
        var userData: UserData
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchProfile(userID) //CALLS GET -> api.github.com/users/username
        call.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>?, response: Response<UserData>?) {
                if (response?.code() == 200) {
                    userData = response.body()
                    val text = "Public Repo : ${userData.repositoryCount}"
                    Glide.with(viewbind.root)
                        .load(userData.avatarUrl)
                        .into(viewbind.circleImageView)
                    viewbind.userID.text = userData.username
                    viewbind.name.text = userData.name
                    viewbind.company.text = userData.company
                    viewbind.repository.text = text
                    supportActionBar?.title = userData.username
                }
            }

            override fun onFailure(call: Call<UserData>?, t: Throwable?) {

            }

        })
        val sectionPageAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = viewbind.viewpager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = viewbind.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getUserData(){
        TODO("HONESTLY I DONT KNOW IF THIS WILL BE USED OR NOT")
    }
}
