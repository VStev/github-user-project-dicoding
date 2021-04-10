package com.submission.githubuser.viewmodelproviders

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.submission.githubuser.databaseapi.AppDatabase.Companion.CONTENT_URI
import com.submission.githubuser.databaseapi.MappingHelper
import com.submission.githubuser.user.SimpleUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favList = MutableLiveData<List<SimpleUserData>>()
    private val mapper = MappingHelper

    fun getfav(context: Context?, user: String?): LiveData<List<SimpleUserData>> {
        if (user != null){
            Log.d("REMINGTON", "I HAVE USER AND ITS $user")
            val uriWithId = Uri.parse("$CONTENT_URI/$user")
            Log.d("REMINGTON", "I HAVE URI AND ITS $uriWithId")
            viewModelScope.launch(Dispatchers.Main) {
                val deferredList = async(Dispatchers.IO) {
                    Log.d("REMINGTON", "REMINGTON-2000 COROUTINE START")
                    val cursor = context?.contentResolver?.query(uriWithId, null, null, null, null)
                    Log.d("REMINGTON", "REMINGTON-2000 COROUTINE MAPPING")
                    mapper.mapCursorToArrayList(cursor)
                }
                favList.postValue(deferredList.await())
            }
        }else {
            Log.d("REMINGTON", "I DONT HAVE USER AND ITS $user")
            viewModelScope.launch(Dispatchers.Main) {
                val deferredList = async(Dispatchers.IO) {
                    Log.d("REMINGTON", "REMINGTON-1000 COROUTINE START")
                    val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                    Log.d("REMINGTON", "REMINGTON-1000 COROUTINE MAPPING")
                    mapper.mapCursorToArrayList(cursor)
                }
                favList.postValue(deferredList.await())
            }
        }
        Log.d("REMINGTON", "REMINGTON-M40 COROUTINE FINISHED")
        return favList
    }

    fun insertToFavorite(context: Context, user: String, avatar: String){
        val values = ContentValues()
        values.put(SimpleUserData.user, user)
        values.put(SimpleUserData.avatar, avatar)
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.insert(CONTENT_URI, values)
        }
    }

    fun deleteFromFavourites(context: Context, user: String){
        val uriWithId = Uri.parse("$CONTENT_URI/$user")
        viewModelScope.launch(Dispatchers.IO) {
        context.contentResolver.delete(uriWithId, null, null)
        }
    }
}