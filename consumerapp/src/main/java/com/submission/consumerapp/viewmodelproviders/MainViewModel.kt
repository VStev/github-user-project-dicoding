package com.submission.consumerapp.viewmodelproviders

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.consumerapp.contentreceiver.DatabaseContract.Companion.CONTENT_URI
import com.submission.consumerapp.contentreceiver.MappingHelper
import com.submission.consumerapp.user.SimpleUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val favList = MutableLiveData<List<SimpleUserData>>()
    private val mapper = MappingHelper

    fun getfav(context: Context?, user: String?): LiveData<List<SimpleUserData>> {
        if (user != null){
            val uriWithId = Uri.parse("$CONTENT_URI/$user")
            viewModelScope.launch(Dispatchers.Main) {
                val deferredList = async(Dispatchers.IO) {
                    val cursor = context?.contentResolver?.query(uriWithId, null, null, null, null)
                    mapper.mapCursorToArrayList(cursor)
                }
                favList.postValue(deferredList.await())
            }
        }else {
            viewModelScope.launch(Dispatchers.Main) {
                val deferredList = async(Dispatchers.IO) {
                    val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                    mapper.mapCursorToArrayList(cursor)
                }
                favList.postValue(deferredList.await())
            }
        }
        return favList
    }
}