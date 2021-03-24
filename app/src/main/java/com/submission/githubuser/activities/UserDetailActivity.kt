package com.submission.githubuser.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.submission.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var viewbind: ActivityUserDetailBinding
    private lateinit var userID: String

    companion object {
        const val EXTRA_USER = "extra user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(viewbind.root)
        userID = intent.getStringExtra(EXTRA_USER).toString()
        showLayout()
    }

    private fun showLayout(){
        TODO("IMPLEMENT THE VIEW+RETROFITINTERACTABLE BUT WITH USER INTERFACE CALL HERE")
    }

    private fun getUserData(){
        TODO("HONESTLY I DONT KNOW IF THIS WILL BE USED OR NOT")
    }
}