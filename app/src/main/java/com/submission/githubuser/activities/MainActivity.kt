package com.submission.githubuser.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.submission.githubuser.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.app_name)
    }
}