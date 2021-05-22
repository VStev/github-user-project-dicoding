package com.submission.githubuser.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuser.R
import com.submission.githubuser.databinding.ActivityUserDetailBinding
import com.submission.githubuser.fragments.SectionsPageAdapter
import com.submission.githubuser.viewmodelproviders.FavouritesViewModel
import com.submission.githubuser.viewmodelproviders.UserDetailViewModel
import com.submission.githubuser.webapi.ApiResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewBind: ActivityUserDetailBinding
    private lateinit var userID: String
    private lateinit var avatarURL: String
    private var favStatus: Boolean = false
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private val favouritesViewModel : FavouritesViewModel by viewModel()

    companion object {
        const val EXTRA_USER = "extra user"

        @StringRes
        private val TAB_TITLES =
            intArrayOf(R.string.follower_tab_header, R.string.following_tab_header)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = intent.getStringExtra(EXTRA_USER).toString()
        this.cacheDir?.delete()
        viewBind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.elevation = 0f
        showLoading(true)
        showLayout()
    }

    private fun showLayout() {
        val sectionPageAdapter = SectionsPageAdapter(this)
        val viewPagerLayout = findViewById<View>(R.id.viewpager_layout)
        val viewPager: ViewPager2 = viewPagerLayout.findViewById(R.id.viewpager)
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = viewPagerLayout.findViewById(R.id.tabs)
        var follower: String
        var following: String
        supportActionBar?.title = userID
        userDetailViewModel.getDetail(userID).observe(this, { response ->
            if (response != null) {
                when (response){
                    is ApiResponse.Success -> {
                        val data = response.data
                        val text = "Public Repo : ${data.repositoryCount}"
                        avatarURL = data.avatarUrl.toString()
                        Glide.with(this)
                            .load(data.avatarUrl)
                            .into(viewBind.circleImageView)
                        viewBind.name.text = data.name
                        if (data.company != null) viewBind.company.text =
                            data.company else viewBind.company.text = getString(R.string.no_company)
                        if (data.location != null) viewBind.location.text =
                            data.location else viewBind.location.text = getString(R.string.no_location)
                        viewBind.repository.text = text
                        follower = data.followersCount.toString()
                        following = data.followingCount.toString()
                        viewBind.button.setOnClickListener (this)
                        showLoading(false)
                        TabLayoutMediator(tabs, viewPager) { tab, position ->
                            val tabTitle =
                                if (position == 0) "${resources.getString(TAB_TITLES[position])} ($follower)" else "${
                                    resources.getString(TAB_TITLES[position])
                                } ($following)"
                            tab.text = tabTitle
                        }.attach()
                    }
                    is ApiResponse.Empty -> {
                        Glide.with(this)
                            .load(R.drawable.crop)
                            .into(viewBind.circleImageView)
                        viewBind.name.text = getString(R.string.something_is_wrong)
                        viewBind.company.text = getString(R.string.wrong_detail)
                        viewBind.location.text = getString(R.string.try_again)
                        viewBind.button.visibility = View.GONE
                        showLoading(false)
                    }
                    is ApiResponse.Error -> {
                        Glide.with(this)
                            .load(R.drawable.crop)
                            .into(viewBind.circleImageView)
                        viewBind.name.text = getString(R.string.something_is_wrong)
                        viewBind.company.text = getString(R.string.wrong_detail)
                        viewBind.location.text = getString(R.string.try_again)
                        viewBind.button.visibility = View.GONE
                        showLoading(false)
                    }
                }
            }
        })
        favouritesViewModel.getFav(userID).observe(this, { SimpleUserData ->
            if (SimpleUserData.isNotEmpty()) {
                viewBind.button.text = resources.getString(R.string.remove_fav)
                favStatus = true
            } else {
                viewBind.button.text = resources.getString(R.string.add_fav)
                favStatus = false
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

    override fun onClick(v: View?) {
        if (v != null) {
            when (favStatus){
                false -> {
                    favouritesViewModel.insertToFavorite(userID, avatarURL)
                    viewBind.button.text = resources.getString(R.string.remove_fav)
                    favStatus = true
                }
                true -> {
                    favouritesViewModel.deleteFromFavourites(userID)
                    viewBind.button.text = resources.getString(R.string.add_fav)
                    favStatus = false
                }
            }
        }
    }
}
