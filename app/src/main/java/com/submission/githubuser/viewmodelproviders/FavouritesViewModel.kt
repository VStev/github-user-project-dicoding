package com.submission.githubuser.viewmodelproviders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.submission.githubuser.user.SimpleUserData

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    fun getfav(): LiveData<List<SimpleUserData>>? {
        return null
    }
}