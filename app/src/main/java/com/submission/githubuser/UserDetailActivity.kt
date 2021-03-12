package com.submission.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var viewbind: ActivityUserDetailBinding
    private lateinit var userDetails: UserData

    companion object {
        const val EXTRA_USER = "extra user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(viewbind.root)
        userDetails = intent.getParcelableExtra(EXTRA_USER)!!
        showLayout()
    }

    private fun showLayout(){
        val followingText =  "${userDetails.followingCount}\nfollowing"
        val followerText = "${userDetails.followersCount}\nfollowers"
        val repositoryText = "${userDetails.repositoryCount}\nrepositories"
        val locationText = "Currently at ${userDetails.location}"
        val companytext = "Works at ${userDetails.company}"
        supportActionBar?.title = "@g${userDetails.username}"
        Glide.with(this)
            .load(userDetails.avatar)
            .apply(RequestOptions().override(600,600))
            .into(viewbind.imageProfile)
        viewbind.textRealname.text = userDetails.name
        viewbind.textLocation.text = locationText
        viewbind.textWorkplace.text = companytext
        viewbind.textFollower.text = followerText
        viewbind.textFollowing.text = followingText
        viewbind.textRepository.text = repositoryText
    }
}