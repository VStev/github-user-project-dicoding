package com.submission.consumerapp.contentreceiver

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

class DatabaseContract {
    companion object {
        const val AUTHORITY = "com.submission.githubuser"
        @RequiresApi(Build.VERSION_CODES.N)
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath("SimpleUserData")
            .build()
    }
}