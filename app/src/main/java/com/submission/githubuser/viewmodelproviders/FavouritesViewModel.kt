package com.submission.githubuser.viewmodelproviders

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.githubuser.databaseapi.AppDatabase.Companion.CONTENT_URI
import com.submission.githubuser.databaseapi.MappingHelper
import com.submission.githubuser.user.SimpleUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favList = MutableLiveData<List<SimpleUserData>>()
    private val mapper = MappingHelper

    fun getfav(context: Context): LiveData<List<SimpleUserData>> {
        GlobalScope.launch(Dispatchers.Main){
            val deferredList = async(Dispatchers.IO) {
                val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
                mapper.mapCursorToArrayList(cursor)
            }
            favList.value = deferredList.await()
        }
        return favList
    }
}