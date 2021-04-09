package com.submission.githubuser.viewmodelproviders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.submission.githubuser.databaseapi.AppDatabase
import com.submission.githubuser.databaseapi.RoomDAO
import com.submission.githubuser.user.SimpleUserData

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favouriteDAO: RoomDAO = AppDatabase.getDatabase(application).userDAO()
    private val favouriteList: LiveData<List<SimpleUserData>> = favouriteDAO.getAll()

    fun getfav(): LiveData<List<SimpleUserData>>{
        return favouriteList
    }
}